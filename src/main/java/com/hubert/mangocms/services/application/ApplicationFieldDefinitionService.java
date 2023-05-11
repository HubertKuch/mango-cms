package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
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

    public ApplicationFieldDefinition addDefinition(String applicationId, CreateDefinition createDefinition) throws
            InvalidRequestException,
            ConflictException {
        if (definitionRepository.existsByName(createDefinition.name())) {
            throw new ConflictException("Field with that name already exists");
        }

        Application application = applicationService.findById(applicationId)
                                                    .orElseThrow(() -> new InvalidRequestException(
                                                            "Application doesn't exists"));

        ApplicationFieldDefinition definition = new ApplicationFieldDefinition(createDefinition.name(),
                createDefinition.isRequired(),
                createDefinition.type(),
                application
        );

        definitionRepository.save(definition);

        return definition;
    }



    public List<ApplicationFieldDefinition> findDefinitionsByApplication(Application application) {
        return definitionRepository.findAllByApplication(application);
    }
}
