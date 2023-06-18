package com.hubert.mangocms.repositories.entities;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityModelRepository extends JpaRepository<EntityModel, String> {
    boolean existsByApplicationAndName(Application application, String name);
    List<EntityModel> findByApplication(Application application);
}
