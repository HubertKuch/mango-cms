package com.hubert.mangocms.domain.models.app;

import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "application_field_definitions")
@OnDelete(action = OnDeleteAction.CASCADE)
public class ApplicationFieldDefinition {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private boolean isRequired;
    private FieldType type;
    private String defaultValue;
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    public ApplicationFieldDefinition(String name, String defaultValue, boolean isRequired, FieldType type, Application application) {
        this.name = name;
        this.isRequired = isRequired;
        this.defaultValue = defaultValue;
        this.type = type;
        this.application = application;
    }
}
