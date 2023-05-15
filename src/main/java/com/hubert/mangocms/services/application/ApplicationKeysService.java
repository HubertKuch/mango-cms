package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.app.ApplicationKeys;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.repositories.application.ApplicationKeysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationKeysService {
    private final ApplicationKeysRepository applicationKeysRepository;

    public ApplicationKeys fetchKeys(User loggedInUser, String applicationId) {
        return applicationKeysRepository.findByIdAndApplication_User(applicationId, loggedInUser);
    }

    public ApplicationKeys saveKeys(ApplicationKeys applicationKeys) {
        return applicationKeysRepository.save(applicationKeys);
    }
}
