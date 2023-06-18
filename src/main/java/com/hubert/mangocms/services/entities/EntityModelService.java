package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.requests.entities.CreateEntityModel;
import com.hubert.mangocms.repositories.entities.EntityModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record EntityModelService(
        EntityModelRepository entityModelRepository
) {
    public List<EntityModel> findByApplication(Application application) {
        return entityModelRepository.findByApplication(application);
    }

    public EntityModel add(Application application, CreateEntityModel createEntityModel) throws ConflictException {
        if (entityModelRepository.existsByApplicationAndName(application, createEntityModel.name())) {
            throw new ConflictException("Entity with that name already exists");
        }

        EntityModel model = EntityModel.builder()
                .name(createEntityModel.name())
                .application(application)
                .build();

        return entityModelRepository.save(model);
    }
}
