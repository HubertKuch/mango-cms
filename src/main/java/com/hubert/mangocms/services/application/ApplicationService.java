package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
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

    public Application createApplication(User user, CreateApplication createApplication) throws ConflictException {
        String applicationName = createApplication.name();

        if (applicationRepository.existsByNameAndUser(applicationName, user)) {
            throw new ConflictException("You have application named %s".formatted(applicationName));
        }

        Application application = new Application(applicationName, user);

        applicationRepository.save(application);

        return application;
    }

    public List<Application> findApplicationsOfUser(User user) {
        return applicationRepository.findByUser(user);
    }

    public Optional<Application> findById(String applicationId) {
        return applicationRepository.findById(applicationId);
    }

    public Application findApplicationOfUser(User user, String applicationId) throws InvalidRequestException {
        return applicationRepository
                .findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new InvalidRequestException("Invalid application id"));
    }

    public Optional<Application> findByIdAndUser(String id, User user) {
        return applicationRepository.findByIdAndUser(id, user);
    }
}
