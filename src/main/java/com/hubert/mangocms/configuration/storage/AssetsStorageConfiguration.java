package com.hubert.mangocms.configuration.storage;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class AssetsStorageConfiguration {
    private String path;
}
