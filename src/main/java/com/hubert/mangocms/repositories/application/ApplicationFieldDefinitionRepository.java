package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationFieldDefinitionRepository extends JpaRepository<ApplicationFieldDefinition, String> {
    List<ApplicationFieldDefinition> findAllByIsRequired(boolean required);
    boolean existsByNameAndApplication(String name, Application application);

    List<ApplicationFieldDefinition> findAllByApplicationAndApplication_User(
            Application application,
            User user
    );

    Optional<ApplicationFieldDefinition> findByApplicationAndId(Application application, String id);
}
