package com.hubert.mangocms.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfiguration {
    private AuthCookieConfiguration cookie;

    @Data
    @Configuration
    public static class AuthCookieConfiguration {
        private String name = "token";
        private boolean isHttpOnly = false;
        private boolean isSecure = false;
        private Duration age = Duration.ofDays(2);
    }
}
