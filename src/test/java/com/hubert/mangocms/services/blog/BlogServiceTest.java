package com.hubert.mangocms.services.blog;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.mappers.FieldRepresentationMapper;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import com.hubert.mangocms.domain.requests.blog.CreateBlogCredentials;
import com.hubert.mangocms.domain.requests.blog.FieldRepresentationCredentials;
import com.hubert.mangocms.repositories.application.ApplicationBlogFieldRepresentationRepository;
import com.hubert.mangocms.repositories.application.ApplicationRepository;
import com.hubert.mangocms.repositories.blog.BlogRepository;
import com.hubert.mangocms.services.application.ApplicationBlogFieldRepresentationService;
import com.hubert.mangocms.services.application.ApplicationFieldDefinitionService;
import com.hubert.mangocms.services.application.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlogServiceTest {

    BlogRepository blogRepository;
    ApplicationBlogFieldRepresentationService applicationBlogFieldRepresentationService;
    ApplicationBlogFieldRepresentationRepository fieldRepresentationRepository;
    ApplicationService applicationService;
    ApplicationRepository applicationRepository;
    FieldRepresentationMapper fieldRepresentationMapper;
    BlogService blogService;
    ApplicationFieldDefinitionService applicationFieldDefinitionService;

    @BeforeEach
    void setUp() {
        blogRepository = mock(BlogRepository.class);
        applicationRepository = mock(ApplicationRepository.class);
        applicationFieldDefinitionService = mock(ApplicationFieldDefinitionService.class);
        applicationService = new ApplicationService(applicationRepository);
        fieldRepresentationMapper = new FieldRepresentationMapper(applicationFieldDefinitionService);
        fieldRepresentationRepository = mock(ApplicationBlogFieldRepresentationRepository.class);
        applicationBlogFieldRepresentationService = new ApplicationBlogFieldRepresentationService(fieldRepresentationRepository);
        blogService = new BlogService(blogRepository, applicationBlogFieldRepresentationService, applicationService, fieldRepresentationMapper);
    }

    @Test
    void givenValidBlogCredentials_thenSave_shouldReturnValidBlog() throws InvalidRequestException {
        List<FieldRepresentationCredentials> fields = List.of(
                new FieldRepresentationCredentials("1", "test"),
                new FieldRepresentationCredentials("2", "test"),
                new FieldRepresentationCredentials("3", "test")
        );
        CreateBlogCredentials blogCredentials = new CreateBlogCredentials(fields);

        when(applicationRepository.findById(anyString())).thenReturn(Optional.of(new Application()));
        when(applicationFieldDefinitionService.findByIdAndApplication(any(), anyString())).thenReturn(Optional.of(new ApplicationFieldDefinition(
                "name", "", true, FieldType.TEXT, new Application()
        )));

        Blog blog = blogService.createBlog("", blogCredentials);

        assertAll(
                () -> assertNotNull(blog),
                () -> assertNotNull(blog.getApplication())
        );
    }
}