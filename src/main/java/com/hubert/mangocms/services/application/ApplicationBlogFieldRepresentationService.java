package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.repositories.application.ApplicationBlogFieldRepresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
final public class ApplicationBlogFieldRepresentationService {
    private final ApplicationBlogFieldRepresentationRepository fieldRepresentationRepository;

    public List<ApplicationBlogFieldRepresentation> findByBlog(Blog blog) {
        return fieldRepresentationRepository.findAllByBlog(blog);
    }

    public List<ApplicationBlogFieldRepresentation> saveAll(List<ApplicationBlogFieldRepresentation> fields) {
        return fieldRepresentationRepository.saveAll(fields);
    }
}
