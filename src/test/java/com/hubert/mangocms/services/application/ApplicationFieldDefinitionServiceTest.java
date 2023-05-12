package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.repositories.application.ApplicationFieldDefinitionRepository;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationFieldDefinitionServiceTest {

    ApplicationRepository applicationRepository;
    ApplicationFieldDefinitionRepository applicationFieldDefinitionRepository;
    ApplicationFieldDefinitionService applicationFieldDefinitionService;

    @BeforeEach
    void setUp() {
        this.applicationRepository = mock(ApplicationRepository.class);
        ApplicationService applicationService = new ApplicationService(applicationRepository);
        this.applicationFieldDefinitionRepository = mock(ApplicationFieldDefinitionRepository.class);

        this.applicationFieldDefinitionService = new ApplicationFieldDefinitionService(applicationService,
                applicationFieldDefinitionRepository
        );
    }

    Application getTestApplication() {
        return new Application(UUID.randomUUID().toString(), "test", null, null, null);
    }

    @Test
    void givenApplication_shouldReturnApplicationDefinitions() throws AuthenticationException {
        Application application = getTestApplication();

        when(applicationFieldDefinitionRepository.findAllByApplicationAndApplication_User(
                any(),
                any()
        )).thenReturn(List.of(
                new ApplicationFieldDefinition("name", true, FieldType.TEXT, application)
        ));
        when(applicationRepository.findByIdAndUser(anyString(), any())).thenReturn(Optional.of(application));

        List<ApplicationFieldDefinition> definitions = applicationFieldDefinitionService.findDefinitionsByApplication(new User(), application.getId());

        assertAll(
                () -> assertEquals(1, definitions.size()),
                () -> assertEquals("name", definitions.get(0).getName())
        );
    }

    @Test
    void givenNotOwnApplication_shouldThrowAuthenticationException() {
        Application application = getTestApplication();

        when(applicationRepository.findByIdAndUser(anyString(), any())).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(AuthenticationException.class, () -> applicationFieldDefinitionService.findDefinitionsByApplication(new User(), application.getId()))
        );
    }
}