package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.app.ApplicationKeys;
import com.hubert.mangocms.repositories.application.ApplicationKeysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationKeysService {
    private final ApplicationKeysRepository applicationKeysRepository;

    public ApplicationKeys findForApplication(String applicationId) {
        return applicationKeysRepository.findApplicationKeysById(applicationId);
    }

    public ApplicationKeys saveKeys(ApplicationKeys applicationKeys) {
        return applicationKeysRepository.save(applicationKeys);
    }
}
