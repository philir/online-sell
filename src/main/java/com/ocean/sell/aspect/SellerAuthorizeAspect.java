package com.ocean.sell.aspect;

import com.ocean.sell.constant.CookieConstant;
import com.ocean.sell.constant.RedisConstant;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellerAuthorizeException;
import com.ocean.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.ocean.sell.controller.Seller*.*(..))" +
    "&& !execution(public * com.ocean.sell.controller.SellerUserController.*(..))")
    public void verify(){

    }

    @Before("verify()")
    public void doVerify(){
        Map<String,String> map = new HashMap<>();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //1.查询cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("[登陆校验] Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //2.去redis查询
       String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("[登陆校验] Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
