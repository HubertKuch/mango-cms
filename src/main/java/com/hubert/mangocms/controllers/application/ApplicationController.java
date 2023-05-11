package com.hubert.mangocms.controllers.application;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.application.CreateApplication;
import com.hubert.mangocms.services.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/application")
final public class ApplicationController {

    private final ApplicationService applicationService;

    @Restricted
    @PostMapping("/")
    public Application createApplication(
            @RequestBody CreateApplication createApplication,
            @RequestAttribute User user
    ) {
        return applicationService.createApplication(user, createApplication);
    }

}
