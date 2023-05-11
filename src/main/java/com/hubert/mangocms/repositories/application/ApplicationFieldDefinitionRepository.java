package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationFieldDefinitionRepository extends JpaRepository<ApplicationFieldDefinition, String> {
    List<ApplicationFieldDefinition> findAllByApplication(Application application);
    boolean existsByName(String name);
}
