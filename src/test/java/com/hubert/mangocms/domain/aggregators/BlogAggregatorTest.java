package com.hubert.mangocms.domain.aggregators;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import com.hubert.mangocms.domain.responses.AggregatedBlog;
import com.hubert.mangocms.services.application.ApplicationBlogFieldRepresentationService;
import com.hubert.mangocms.services.blog.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlogAggregatorTest {

    FieldAggregator fieldAggregator;
    BlogService blogService;
    ApplicationBlogFieldRepresentationService representationService;
    BlogAggregator blogAggregator;

    @BeforeEach
    void setUp() {
        fieldAggregator = new FieldAggregator();
        blogService = mock(BlogService.class);
        representationService = mock(ApplicationBlogFieldRepresentationService.class);
        blogAggregator = new BlogAggregator(blogService, fieldAggregator, representationService);
    }

    @Test
    void givenValidBlogAndFields_shouldParseToAggregatedResponse() throws InvalidRequestException {
        Blog blog = new Blog(UUID.randomUUID().toString(), null, null, null);

        when(blogService.findById(anyString())).thenReturn(Optional.of(blog));

        ApplicationFieldDefinition ageDefinition = new ApplicationFieldDefinition("age", "",true, FieldType.INTEGER, null);
        ApplicationFieldDefinition nameDefinition = new ApplicationFieldDefinition("name","", true, FieldType.TEXT, null);

        when(representationService.findByBlog(eq(blog))).thenReturn(List.of(
                new ApplicationBlogFieldRepresentation( "19", ageDefinition, blog),
                new ApplicationBlogFieldRepresentation("Janek", nameDefinition, null)
        ));

        AggregatedBlog aggregatedBlog = blogAggregator.aggregatedBlog(blog.getId());

        assertAll(
                () -> assertNotNull(aggregatedBlog),
                () -> assertFalse(aggregatedBlog.fields().isEmpty()),
                () -> assertInstanceOf(Integer.class, aggregatedBlog.fields().get(0).value())
        );
    }
}