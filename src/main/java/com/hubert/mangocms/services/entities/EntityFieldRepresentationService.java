package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.entities.FieldDefinitionDoesntExistsException;
import com.hubert.mangocms.domain.models.entities.RawFieldRepresentation;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import com.hubert.mangocms.domain.models.entities.fields.representation.EntityFieldRepresentation;
import com.hubert.mangocms.repositories.entities.EntityFieldRepresentationRepository;

import java.util.ArrayList;
import java.util.List;

public record EntityFieldRepresentationService(
        EntityFieldRepresentationRepository representationRepository,
        EntityFieldDefinitionService definitionService
) {
    public EntityFieldRepresentation persist(RawFieldRepresentation rawFieldRepresentation) throws FieldDefinitionDoesntExistsException {
        EntityFieldDefinition fieldDefinition = definitionService.findById(rawFieldRepresentation.definitionId()).orElseThrow(FieldDefinitionDoesntExistsException::new);

        EntityFieldRepresentation representation = EntityFieldRepresentation.builder()
                .value(rawFieldRepresentation.value())
                .definition(fieldDefinition)
                .build();


        return representationRepository.save(representation);
    }

    public List<EntityFieldRepresentation> persist(List<RawFieldRepresentation> rawRepresentations) throws FieldDefinitionDoesntExistsException {
        List<EntityFieldRepresentation> representations = new ArrayList<>(rawRepresentations.size());

        for (RawFieldRepresentation representation : rawRepresentations) {
            representations.add(persist(representation));
        }

        return representations;
    }
}
