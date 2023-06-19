package com.hubert.mangocms.controllers.application.entities;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.entities.CreateEntityModel;
import com.hubert.mangocms.services.entities.EntityModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications/{applicationId}/entities/")
public record EntitiesController(EntityModelService entityModelService) {
    @Restricted
    @PostMapping
    public EntityModel createEntity(
            Application application,
            @RequestBody CreateEntityModel data
    ) throws ConflictException {
        return entityModelService.add(application, data);
    }

    @Restricted
    @GetMapping
    public List<EntityModel> getUserEntities(
            Application application
    ) {
        return entityModelService.findByApplication(application);
    }
}
