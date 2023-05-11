package com.hubert.mangocms.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {
    private String secret;
    private String issuer;
    private Duration expireDuration;
}
