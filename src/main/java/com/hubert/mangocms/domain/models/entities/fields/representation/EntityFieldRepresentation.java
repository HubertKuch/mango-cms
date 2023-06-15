package com.hubert.mangocms.domain.models.entities.fields.representation;

import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "entity_field_representation")
public class EntityFieldRepresentation {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String value;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "definition_id", referencedColumnName = "id")
    private EntityFieldDefinition definition;
}
