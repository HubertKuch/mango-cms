package com.hubert.mangocms.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {
    private String secret = "secret";
    private String issuer = "mango";
    private Duration expireDuration = Duration.ofDays(2);
}
