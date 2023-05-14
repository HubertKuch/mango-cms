package com.hubert.mangocms.domain.mappers;

import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.requests.blog.FieldRepresentationCredentials;
import com.hubert.mangocms.services.application.ApplicationFieldDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FieldRepresentationMapper {
    private final ApplicationFieldDefinitionService applicationFieldDefinitionService;

    public List<ApplicationBlogFieldRepresentation> fromCredentials(
            Blog blog, List<FieldRepresentationCredentials> credentials
    ) {
        return credentials.stream().map(field -> {
            ApplicationFieldDefinition fieldDefinition = applicationFieldDefinitionService
                    .findByIdAndApplication(blog.getApplication(), field.definitionId())
                    .orElse(null);

            return new ApplicationBlogFieldRepresentation(field.value(), fieldDefinition, blog);
        }).collect(Collectors.toList());
    }

}
