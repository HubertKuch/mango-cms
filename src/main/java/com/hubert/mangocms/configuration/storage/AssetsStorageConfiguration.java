package com.hubert.mangocms.configuration.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "assets")
public class AssetsStorageConfiguration {
    private String path;
}
