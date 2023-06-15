package com.hubert.mangocms.domain.models.entities;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "entity_model")
public class EntityModel {
    @Id
    @Builder.Default
    private final String id = UUID.randomUUID().toString();
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "application_id")
    private List<EntityFieldDefinition> definitions;
}
