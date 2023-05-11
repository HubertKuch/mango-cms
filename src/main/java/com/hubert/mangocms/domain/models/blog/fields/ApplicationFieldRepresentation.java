package com.hubert.mangocms.domain.models.blog.fields;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "blogs")
@RequiredArgsConstructor
public class ApplicationFieldRepresentation {
    @Id
    private String id = UUID.randomUUID().toString();
    private String defaultValue;
    private String value;
    @ManyToOne
    @JoinColumn(name = "field_definition_id")
    private ApplicationFieldDefinition definition;

    public ApplicationFieldRepresentation(String defaultValue, String value, ApplicationFieldDefinition definition) {
        this.defaultValue = defaultValue;
        this.value = value;
        this.definition = definition;
    }
}
