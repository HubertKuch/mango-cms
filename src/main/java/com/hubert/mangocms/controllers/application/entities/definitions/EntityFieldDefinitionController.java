package com.hubert.mangocms.controllers.application.entities.definitions;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.entities.fields.definition.EntityFieldDefinition;
import com.hubert.mangocms.domain.requests.entities.CreateFieldDefinition;
import com.hubert.mangocms.services.entities.EntityFieldDefinitionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/applications/{applicationId}/entities/{entityModelId}/definitions/")
public record EntityFieldDefinitionController(EntityFieldDefinitionService definitionService) {
    @Restricted
    @PostMapping
    public EntityFieldDefinition createDefinition(
            EntityModel entityModel,
            @RequestBody CreateFieldDefinition body
    ) throws ConflictException {
        return definitionService.addDefinitionToEntity(entityModel, body);
    }
}
