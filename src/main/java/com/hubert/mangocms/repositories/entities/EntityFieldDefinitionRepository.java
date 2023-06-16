package com.hubert.mangocms.repositories.entities;

import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityFieldDefinitionRepository extends JpaRepository<EntityFieldDefinition, String> {
    boolean existsByModelAndName(EntityModel model, String name);
}
