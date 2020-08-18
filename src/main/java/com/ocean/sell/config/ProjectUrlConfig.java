package com.ocean.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project-url")
public class ProjectUrlConfig {

    //微信公众平台授权url
    public String wechatMpAuthorize;

    //微信开放平台授权Url
    public String wechatOpenAuthorize;

    //点餐系统
    public String sell;
}

