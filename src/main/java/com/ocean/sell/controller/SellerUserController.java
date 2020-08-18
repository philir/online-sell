package com.ocean.sell.controller;

import com.lly835.bestpay.rest.type.Post;
import com.ocean.sell.config.ProjectUrlConfig;
import com.ocean.sell.constant.CookieConstant;
import com.ocean.sell.constant.RedisConstant;
import com.ocean.sell.dataobject.SellerInfo;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.form.SellerForm;
import com.ocean.sell.service.SellerService;
import com.ocean.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

   /* *//**
     * 登陆
     * @param openid
     * @param response
     * @param map
     * @return
     *//*
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg",ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //2.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);
        //3.设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        return new ModelAndView("redirect:/seller/order/list");
    }

    *//**
     * 注销
     *//*
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }*/

    /**
     *添加和修改
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/login")
    public ModelAndView save(@Valid SellerForm form,
                             BindingResult bindingResult,
                             HttpServletResponse response,
                             Map<String,Object> map) {
        //1.检查表单是否有错误
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/login");
            return new ModelAndView("common/error", map);
        }
        //2.到数据库查找是否有该用户
        SellerInfo sellerInfo = sellerService.findSellerInfoById(form.getUsername());
        //没有该用户
        if(sellerInfo == null){
            map.put("msg",ResultEnum.USER_NOT_EXIST.getMessage());
            map.put("url","/sell/weixin/login");
            return new ModelAndView("common/error",map);
        }
        //密码错误
        if(!form.getPassword().equalsIgnoreCase(sellerInfo.getPassword())){
            map.put("msg",ResultEnum.USER_PASSWORD_INCORRECT.getMessage());
            map.put("url","/sell/weixin/login");
            return new ModelAndView("common/error",map);
        }
        //3.登录成功.设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),form.getUsername(),expire,TimeUnit.SECONDS);
        //4.设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);
        map.put("msg",ResultEnum.LOGIN_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /*
       注册
     */
    @PostMapping("/signUp")
    public ModelAndView signUp(@Valid SellerForm form,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map){
        //1.检查表单是否有错误
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/signUp");
            return new ModelAndView("common/error", map);
        }
        //2.到数据库查找是否有该用户
        SellerInfo sellerInfo = sellerService.findSellerInfoById(form.getUsername());
        //该用户存在
        if(sellerInfo != null){
            map.put("msg",ResultEnum.USER_EXIST.getMessage());
            map.put("url","/sell/weixin/signUp");
            return new ModelAndView("common/error",map);
        }
        SellerInfo seller = new SellerInfo();
        seller.setUsername(form.getUsername());
        seller.setPassword(form.getPassword());
        sellerInfo = sellerService.insertSellerInfo(seller);
        if(sellerInfo == null){
            map.put("msg",ResultEnum.SIGNUP_FAIL.getMessage());
            map.put("url","/sell/weixin/signUp");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.USER_SUCCESS_SIGNUP.getMessage());
        map.put("url","/sell/weixin/login");
        return new ModelAndView("common/success",map);
    }

    /*
      注销
    */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2.清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/sell/weixin/login");
        return new ModelAndView("common/success",map);
    }
}
