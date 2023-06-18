package com.hubert.mangocms.controllers.entities;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.AuthorizationException;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.entities.CreateEntityModel;
import com.hubert.mangocms.middleware.UserApplicationMiddleware;
import com.hubert.mangocms.services.entities.EntityModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications/{applicationId}/entities/")
public record EntitiesController(EntityModelService entityModelService, UserApplicationMiddleware middleware) {
    @Restricted
    @PostMapping
    public EntityModel createEntity(
            Application application,
            @RequestBody CreateEntityModel data,
            @RequestAttribute User user
    ) throws ConflictException, AuthorizationException {
        middleware.userMustBeOwnerById(user, application.getId());

        return entityModelService.add(application, data);
    }

    @Restricted
    @GetMapping
    public List<EntityModel> getUserEntities(
            Application application,
            @RequestAttribute User user
    ) throws AuthorizationException {
        middleware.userMustBeOwnerById(user, application.getId());

        return entityModelService.findByApplication(application);
    }
}
