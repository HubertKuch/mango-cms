package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;
import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.application.CreateDefinition;
import com.hubert.mangocms.repositories.application.ApplicationFieldDefinitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationFieldDefinitionService {
    private final ApplicationService applicationService;
    private final ApplicationFieldDefinitionRepository definitionRepository;

    public ApplicationFieldDefinition addDefinition(
            User user, String applicationId, CreateDefinition createDefinition
    ) throws InvalidRequestException, ConflictException, AuthenticationException {
        Application application = applicationService.findById(applicationId)
                                                    .orElseThrow(() -> new InvalidRequestException(
                                                            "Application doesn't exists"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new AuthenticationException("You cannot delete that field");
        }


        if (definitionRepository.existsByNameAndApplication(createDefinition.name(), application)) {
            throw new ConflictException("Field with that name already exists");
        }

        ApplicationFieldDefinition definition = new ApplicationFieldDefinition(createDefinition.name(),
                createDefinition.isRequired(),
                createDefinition.type(),
                application
        );

        definitionRepository.save(definition);

        return definition;
    }

    public ApplicationFieldDefinition removeDefinition(User user, String applicationId, String definitionId) throws
            InvalidRequestException,
            AuthenticationException {
        Application application = applicationService.findById(applicationId)
                                                    .orElseThrow(() -> new InvalidRequestException(
                                                            "Application doesn't exists"));

        if (!application.getUser().getId().equals(user.getId())) {
            throw new AuthenticationException("You cannot delete that field");
        }

        ApplicationFieldDefinition fieldDefinition = definitionRepository.findById(definitionId)
                                                                         .orElseThrow(() -> new InvalidRequestException(
                                                                                 "Field doesn't exists on this application"));

        definitionRepository.deleteById(definitionId);

        return fieldDefinition;
    }

    public List<ApplicationFieldDefinition> findDefinitionsByApplication(User user, String applicationId) throws
            AuthenticationException {
        Application application = applicationService.findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new AuthenticationException("This isn't your application"));

        return definitionRepository.findAllByApplicationAndApplication_User(application, user);
    }
}
