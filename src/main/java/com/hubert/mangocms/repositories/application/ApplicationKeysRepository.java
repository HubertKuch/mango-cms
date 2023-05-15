package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.ApplicationKeys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationKeysRepository extends JpaRepository<ApplicationKeys, String> {
    ApplicationKeys findApplicationKeysById(String id);
}
