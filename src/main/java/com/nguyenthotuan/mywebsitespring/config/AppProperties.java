package com.nguyenthotuan.mywebsitespring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
// @PropertySource("classpath:app.yml")
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String hostName;
    private String hostProtocol;
    private String uploadLocation;
}
