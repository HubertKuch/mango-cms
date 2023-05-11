package com.hubert.mangocms.domain.models.app;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationKeys {
    @Id
    private String id;
    private String unique;
    private String apiKey;

    public static ApplicationKeys from(Application application) {
        return new ApplicationKeys(application.getId(), RandomStringUtils.random(10), UUID.randomUUID().toString());
    }
}
