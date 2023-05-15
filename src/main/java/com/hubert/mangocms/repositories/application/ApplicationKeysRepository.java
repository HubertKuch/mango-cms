package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.ApplicationKeys;
import com.hubert.mangocms.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationKeysRepository extends JpaRepository<ApplicationKeys, String> {
    ApplicationKeys findByIdAndApplication_User(String id, User application_user);
}
