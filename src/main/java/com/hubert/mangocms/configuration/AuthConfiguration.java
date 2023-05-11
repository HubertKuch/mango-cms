package com.hubert.mangocms.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfiguration {
    private AuthCookieConfiguration authCookieConfiguration;

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "cookie")
    public static class AuthCookieConfiguration {
        private String name;
        private boolean isHttpOnly;
        private boolean isSecure;
    }
}
