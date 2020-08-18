package com.ocean.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "image")
public class ImageConfig {

    //本地上传路经
    private String uploadFolder;
}
