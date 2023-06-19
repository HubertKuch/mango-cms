package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import com.hubert.mangocms.domain.requests.entities.CreateFieldDefinition;
import com.hubert.mangocms.repositories.entities.EntityFieldDefinitionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record EntityFieldDefinitionService(
        EntityFieldDefinitionRepository entityFieldDefinitionRepository
) {
    public EntityFieldDefinition addDefinitionToEntity(EntityModel entityModel, CreateFieldDefinition createFieldDefinition) throws ConflictException {
        if (entityFieldDefinitionRepository.existsByModelAndName(entityModel, createFieldDefinition.name())) {
            throw new ConflictException("Field with that name already exists on that model");
        }

        EntityFieldDefinition entityFieldDefinition = EntityFieldDefinition
                .builder()
                .name(createFieldDefinition.name())
                .isNullable(createFieldDefinition.isNullable())
                .model(entityModel)
                .type(createFieldDefinition.type())
                .build();

        return entityFieldDefinitionRepository.save(entityFieldDefinition);
    }

    public Optional<EntityFieldDefinition> findById(String id) {
        return entityFieldDefinitionRepository.findById(id);
    }
}
