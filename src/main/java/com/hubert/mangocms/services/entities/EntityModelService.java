package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.internal.AuthorizationException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.entities.CreateEntityModel;
import com.hubert.mangocms.middleware.UserApplicationMiddleware;
import com.hubert.mangocms.repositories.entities.EntityModelRepository;
import com.hubert.mangocms.services.application.ApplicationService;
import org.springframework.stereotype.Service;

@Service
public record EntityModelService(
        EntityModelRepository entityModelRepository,
        UserApplicationMiddleware userApplicationMiddleware,
        ApplicationService applicationService
) {
    public EntityModel add(User loggedInUser, String applicationId, CreateEntityModel createEntityModel) throws AuthorizationException {
        userApplicationMiddleware.userMustBeOwnerById(loggedInUser, applicationId);

        Application application = applicationService.findById(applicationId).get();

        EntityModel entityModel = EntityModel.builder()
                .name(createEntityModel.name())
                .application(application)
                .build();

        return entityModelRepository.save(entityModel);
    }
}
