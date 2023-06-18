package com.hubert.mangocms.controllers.application;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.application.CreateApplication;
import com.hubert.mangocms.services.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
public record ApplicationController(ApplicationService applicationService)  {
    @Restricted
    @GetMapping("/")
    public List<Application> getApplications(@RequestAttribute User user) {
        return applicationService.findApplicationsOfUser(user);
    }

    @Restricted
    @PostMapping("/")
    public Application createApplication(
            @RequestBody CreateApplication createApplication,
            @RequestAttribute User user
    ) throws ConflictException {
        return applicationService.createApplication(user, createApplication);
    }
}
