package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.requests.application.CreateApplication;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
final public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application createApplication(CreateApplication createApplication) {
        return new Application();
    }
}
