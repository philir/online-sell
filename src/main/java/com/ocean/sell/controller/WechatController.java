package com.ocean.sell.controller;

import com.alibaba.fastjson.JSONObject;
import com.ocean.sell.config.ProjectUrlConfig;
import com.ocean.sell.dataobject.BuyerInfo;
import com.ocean.sell.dataobject.SellerInfo;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.enums.VipEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.service.BuyerService;
import com.ocean.sell.service.SellerService;
import com.ocean.sell.service.WeChatAuthService;
import com.ocean.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WeChatAuthService weChatAuthService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWechatMpAuthorize ()+ "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO,URLEncoder.encode(returnUrl));
        log.info("[微信网页授权] 获取code，result={}",redirectUrl);

        return "redirect:" + redirectUrl;
        //return "redirect:" + returnUrl + "?openid=" + "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        WxMpUser wxMpUser;
        String openId;
        BuyerInfo buyerInfo;
        String buyerId = KeyUtil.genUniqueKey();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            openId = wxMpOAuth2AccessToken.getOpenId();
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,openId);
            if("oJEsx1oumaGzA3uKRjkUE75fCvdk".equalsIgnoreCase(openId)){
                openId = "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";
            }else{
                openId = wxMpUser.getOpenId();
            }
            buyerInfo = buyerService.findBuyerInfoByOpenid(openId);
            if(buyerInfo == null) {
                buyerInfo = new BuyerInfo();
                buyerInfo.setBuyerId(buyerId);
                buyerInfo.setAddressId("");
                buyerInfo.setBuyerOpenid(openId);
                buyerInfo.setBuyerNickname(wxMpUser.getNickname());
                buyerInfo.setBuyerHeadimgurl(wxMpUser.getHeadImgUrl());
                buyerInfo.setBuyerVip(VipEnum.NOTVIP.getCode());
                BuyerInfo result = buyerService.insertBuyerInfo(buyerInfo);
                if(result == null){
                    log.error("[微信网页授权] 买家信息添加失败");
                    throw new SellException(ResultEnum.BUYERINFO_ADD_FAIL);
                }
            }
        }catch (WxErrorException e){
            log.error("[微信网页授权] {}",e);
            throw new SellException(ResultEnum.WXCHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }

        return "redirect:" + returnUrl + "?openid=" + openId;
        /*return "redirect:" + returnUrl + "?openid=" + "oTgZpwY0gi26ntYJ1N-O5Q7QO9Ls";*/
        //return "redirect:" + returnUrl + "?openid=" + "oJEsx1oumaGzA3uKRjkUE75fCvdk";
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl){
        String url = projectUrlConfig.getWechatOpenAuthorize() +  "/sell/wechat/userInfo/qrUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.error("[微信登陆] {}",e);
            throw new SellException(ResultEnum.WXCHAT_OPEN_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("[微信登陆] 获取openId={}",openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }


    /*//pc点击微信登录，生成登录二维码
    @RequestMapping(value = "/wxLoginPage",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public Map<String,String> wxLoginPage(HttpServletRequest request) throws Exception {
        String sessionId = request.getSession().getId();
        log.info("sessionId:"+sessionId);
        String uri = weChatAuthService.getAuthorizationUrl("pc",sessionId);//设置redirect_uri和state=sessionId以及测试号信息，返回授权url
        log.info("sessionId:" + uri);
        Map<String,String> map = new HashMap<>();
        map.put("sessionId", sessionId);
        map.put("uri", uri);//用来前端生成二维码
        return map;
    }

    //扫描二维码授权成功，取到code，回调方法
    @RequestMapping(value = "/pcAuth")
    @ResponseBody
    @CrossOrigin
    public String pcCallback(String code, String state,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Map<String,String> map) throws Exception {
        String result = weChatAuthService.getAccessToken(code);//根据code获取access_token和openId，不懂看微信文档
        JSONObject jsonObject = JSONObject.parseObject(result);

        //String refresh_token = jsonObject.getString("refresh_token");
        String access_token = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openId");

        JSONObject infoJson = weChatAuthService.getUserInfo(access_token,openId);//根据token和openId获取微信用户信息，不懂看我上一篇文章开始分享的链接
        if(infoJson!=null){
            infoJson.put("openId", openId);
            redisTemplate.opsForValue().set(state, infoJson, 10*60, TimeUnit.SECONDS);
            return "redirect:" + "http://philir.natapp1.cc/sell/seller/order/list";
        }
        return "redirect:" + "http://philir.natapp1.cc/sell/weixin/login";
    }

    //轮询查询key
    @RequestMapping(value="/polling",method=RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public Map<String, Object> polling(String sessionId,HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        if (redisTemplate.hasKey(sessionId)) {
            JSONObject infoJson = (JSONObject) redisTemplate.opsForValue().get(sessionId);
            redisTemplate.opsForValue().getOperations().delete(sessionId);
            String openId = (String) infoJson.get("openId");
            //根据openId判断我们网站是否存在该用户，数据库用户表会保存用户
            SellerInfo user = sellerService.findSellerInfoByOpenid(openId);
            if (user == null) {
                SellerInfo newUser = new SellerInfo();
                newUser.setUsername("philir");
                newUser.setPassword("123456");
                SellerInfo result = sellerService.insertSellerInfo(newUser);//新增用户
                if (result == null) {
                    resultMap.put("status", 500);
                    resultMap.put("message", "登录失败:");
                    return resultMap;
                }
            }
            //登录操作
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(openId, openId);//这里是用shiro登录，反正该openId已经微信扫码验证
                SecurityUtils.getSubject().login(token);
                resultMap.put("status", 200);
                resultMap.put("message", "登录成功");

              *//*  //更新用户最后登录时间
                Subject currentUser = SecurityUtils.getSubject();
                SellerInfo luser = (SellerInfo) currentUser.getPrincipal();
                SellerInfo user1 = new SellerInfo();
                user1.setId(luser.getId());
                userService.updateUserByIdSelective(user1);//更新用户方法*//*

            } catch (Exception e) {
                resultMap.put("message", "未知系统错误:" + e.getMessage());
            }
            return resultMap;
        } else {//not has key
            resultMap.put("status", 0);
            return resultMap;
        }
    }*/

}
