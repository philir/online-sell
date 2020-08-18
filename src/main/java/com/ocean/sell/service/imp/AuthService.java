package com.ocean.sell.service.imp;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    public abstract String getAccessToken(String code);
    public abstract String getOpenId(String accessToken);
    public abstract String refreshToken(String code);
    public abstract String getAuthorizationUrl(String type,String state) throws UnsupportedEncodingException;
    public abstract JSONObject getUserInfo(String accessToken, String openId);
}
