package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, String> {
    List<Application> findByUser(User user);

    Optional<Application> findByIdAndUser(String id, User user);
}
