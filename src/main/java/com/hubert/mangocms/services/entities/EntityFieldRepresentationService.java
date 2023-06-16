package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.entities.FieldDefinitionDoesntExistsException;
import com.hubert.mangocms.domain.exceptions.entities.FieldValidationException;
import com.hubert.mangocms.domain.models.entities.RawFieldRepresentation;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import com.hubert.mangocms.domain.models.entities.fields.representation.EntityFieldRepresentation;
import com.hubert.mangocms.repositories.entities.EntityFieldRepresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EntityFieldRepresentationService {
    private final EntityFieldRepresentationRepository representationRepository;
    private final EntityFieldDefinitionService definitionService;

    @Transactional
    public EntityFieldRepresentation persist(RawFieldRepresentation rawFieldRepresentation) throws FieldDefinitionDoesntExistsException, FieldValidationException {
        EntityFieldDefinition fieldDefinition = definitionService.findById(rawFieldRepresentation.definitionId()).orElseThrow(FieldDefinitionDoesntExistsException::new);

        if (rawFieldRepresentation.value() == null && !fieldDefinition.isNullable()) {
            throw new FieldValidationException("That field cannot be null");
        }

        EntityFieldRepresentation representation = EntityFieldRepresentation.builder()
                .value(rawFieldRepresentation.value())
                .definition(fieldDefinition)
                .build();


        return representationRepository.save(representation);
    }

    public List<EntityFieldRepresentation> persist(List<RawFieldRepresentation> rawRepresentations) throws FieldDefinitionDoesntExistsException, FieldValidationException {
        List<EntityFieldRepresentation> representations = new ArrayList<>(rawRepresentations.size());

        for (RawFieldRepresentation representation : rawRepresentations) {
            representations.add(persist(representation));
        }

        return representations;
    }
}
