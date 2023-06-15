package com.hubert.mangocms.domain.models.entities.fields.definition;

import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.entities.fields.representation.EntityFieldRepresentation;
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
@Entity(name = "entity_field_definition")
public class EntityFieldDefinition {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    @Enumerated(EnumType.STRING)
    private FieldType type;
    private boolean isNullable;
    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private EntityModel model;
}
