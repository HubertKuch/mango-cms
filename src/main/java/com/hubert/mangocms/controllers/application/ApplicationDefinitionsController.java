package com.hubert.mangocms.controllers.application;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.application.CreateDefinition;
import com.hubert.mangocms.services.application.ApplicationFieldDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applications/{applicationId}/definitions")
final public class ApplicationDefinitionsController {
    private final ApplicationFieldDefinitionService applicationFieldDefinitionService;

    @Restricted
    @GetMapping("/")
    public List<ApplicationFieldDefinition> findApplicationDefinitions(
            @RequestAttribute User user, @PathVariable String applicationId
    ) throws AuthenticationException {
        return applicationFieldDefinitionService.findDefinitionsByApplication(user, applicationId);
    }

    @Restricted
    @PostMapping("/")
    public ApplicationFieldDefinition create(
            @RequestBody CreateDefinition createDefinition,
            @PathVariable String applicationId,
            @RequestAttribute User user
    ) throws ConflictException, AuthenticationException, InvalidRequestException {
        return applicationFieldDefinitionService.addDefinition(user, applicationId, createDefinition);
    }
}
