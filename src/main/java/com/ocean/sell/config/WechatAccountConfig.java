package com.ocean.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    //测试公众号Id
    private String mpAppIdTest;
    //公众平台Id
    private String mpAppId;
    //公众平台密钥
    private String mpAppSecret;
    //开放平台id
    private String openAppId;
    //开放平台密钥
    private String openAppSecret;
    //商户号
    private String mchId;
    //商户密钥
    private String mchKey;
    //商户证书路径
    private String keyPath;
    //微信支付异步通知地址
    private String notifyUrl;
    //消息模板Id
    private Map<String,String> templateId;
}
