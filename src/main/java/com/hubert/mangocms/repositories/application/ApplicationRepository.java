package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.app.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, String> {}
