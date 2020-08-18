package com.ocean.sell.service;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

public interface WeChatAuthService {
    String getAuthorizationUrl(String type,String state) throws UnsupportedEncodingException;

    String getAccessToken(String code);

    String getOpenId(String accessToken);

    JSONObject getUserInfo(String accessToken, String openId);

    String refreshToken(String refresh_token);
}
