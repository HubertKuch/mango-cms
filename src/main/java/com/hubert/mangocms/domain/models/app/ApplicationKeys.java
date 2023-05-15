package com.hubert.mangocms.domain.models.app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.UUID;

@Data
@Entity(name = "application_keys")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ApplicationKeys {
    @Id
    @Column(name = "application_id", updatable = false, nullable = false)
    private String id;
    @Column(unique = true, name = "`unique`")
    private String unique;
    @Column(unique = true)
    private String apiKey;

    public static ApplicationKeys from(Application application) {
        String appApiKey = UUID.randomUUID().toString();
        String appUnique = RandomStringUtils.random(10, true, false);

        return new ApplicationKeys(application.getId(), appUnique, appApiKey);
    }
}
