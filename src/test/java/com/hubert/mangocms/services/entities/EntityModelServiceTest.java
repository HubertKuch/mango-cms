package com.hubert.mangocms.services.entities;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.domain.requests.entities.CreateEntityModel;
import com.hubert.mangocms.repositories.entities.EntityModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EntityModelServiceTest {
    private EntityModelRepository entityModelRepository;
    private EntityModelService entityModelService;

    @BeforeEach
    void setUp() {
        entityModelRepository = mock(EntityModelRepository.class);
        entityModelService = new EntityModelService(entityModelRepository);
    }

    @Test
    void givenValidNotExistingModel_shouldSave() throws ConflictException {
        String name = "Post";
        Application application = new Application();
        CreateEntityModel createEntityModel = new CreateEntityModel(name);

        when(entityModelRepository.existsByApplicationAndName(eq(application), eq(name))).thenReturn(false);
        when(entityModelRepository.save(any())).thenReturn(EntityModel.builder()
                .name(name)
                .application(application)
                .build()
        );

        EntityModel model = entityModelService.add(application, createEntityModel);

        assertEquals(name, model.getName());
        assertEquals(application, model.getApplication());
    }

    @Test
    void givenValidExistingModel_shouldThrowException()  {
        String name = "Post";
        Application application = new Application();
        CreateEntityModel createEntityModel = new CreateEntityModel(name);

        when(entityModelRepository.existsByApplicationAndName(eq(application), eq(name))).thenReturn(true);
        when(entityModelRepository.save(any())).thenReturn(EntityModel.builder()
                .name(name)
                .application(application)
                .build()
        );

        assertThrows(ConflictException.class, () -> entityModelService.add(application, createEntityModel));
    }
}