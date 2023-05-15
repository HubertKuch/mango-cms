package com.hubert.mangocms.configuration.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageConfiguration {
    private String path;
    private AssetsStorageConfiguration assetsStorageConfiguration;
}
