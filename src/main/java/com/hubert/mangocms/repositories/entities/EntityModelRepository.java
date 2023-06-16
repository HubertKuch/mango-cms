package com.hubert.mangocms.repositories.entities;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityModelRepository extends JpaRepository<EntityModel, String> {
    boolean existsByApplicationAndName(Application application, String name);
}
