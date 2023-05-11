package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.application.CreateApplication;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
final public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public Application createApplication(User user, CreateApplication createApplication) {
        Application application = new Application(createApplication.name(), user);

        applicationRepository.save(application);

        return application;
    }

    public List<Application> findApplicationsOfUser(User user) {
        return applicationRepository.findByUser(user);
    }

    public Optional<Application> findById(String applicationId) {
        return applicationRepository.findById(applicationId);
    }
}
