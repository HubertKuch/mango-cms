package com.hubert.mangocms.domain.models.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

@Data
@Entity(name = "application_keys")
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationKeys {
    @Id
    private String id;
    @Column(unique = true)
    private String unique;
    @Column(unique = true)
    private String apiKey;

    public static ApplicationKeys from(Application application) {
        return new ApplicationKeys(application.getId(), RandomStringUtils.random(10), UUID.randomUUID().toString());
    }
}
