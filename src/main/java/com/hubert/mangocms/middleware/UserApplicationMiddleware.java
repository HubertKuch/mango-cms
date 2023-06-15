package com.hubert.mangocms.middleware;

import com.hubert.mangocms.domain.exceptions.internal.AuthorizationException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.services.application.ApplicationService;
import com.hubert.mangocms.services.user.UserService;
import org.springframework.stereotype.Component;

@Component
public record UserApplicationMiddleware(
        ApplicationService applicationService,
        UserService userService
) {
    public void userMustBeOwnerById(User user, String applicationId) throws AuthorizationException {
        applicationService.findByIdAndUser(applicationId, user)
                .orElseThrow(AuthorizationException::new);
    }
}
