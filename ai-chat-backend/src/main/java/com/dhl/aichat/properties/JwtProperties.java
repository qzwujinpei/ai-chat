package com.dhl.aichat.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "itheima.jwt")
public class JwtProperties {
    private String secretKey;
    private long ttlMillis;
    private String tokenName;
}
