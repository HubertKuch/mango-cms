package com.hubert.mangocms.controllers.application;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.models.app.ApplicationKeys;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.services.application.ApplicationKeysService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications/{applicationId}/keys")
public class ApplicationKeysController {
    private final ApplicationKeysService applicationKeysService;

    @Restricted
    @GetMapping("/")
    public ApplicationKeys getKeys(@RequestAttribute User user, @PathVariable String applicationId) {
        return applicationKeysService.fetchKeys(user, applicationId);
    }
}
