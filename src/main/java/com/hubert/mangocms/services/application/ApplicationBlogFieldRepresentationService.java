package com.hubert.mangocms.services.application;

import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.repositories.application.ApplicationBlogFieldRepresentationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationBlogFieldRepresentationService {
    private final ApplicationBlogFieldRepresentationRepository fieldRepresentationRepository;

    public List<ApplicationBlogFieldRepresentation> findByBlog(Blog blog) {
        return fieldRepresentationRepository.findAllByBlog(blog);
    }

    @Transactional(rollbackOn = Throwable.class)
    public List<ApplicationBlogFieldRepresentation> replaceWith(Blog blog, List<ApplicationBlogFieldRepresentation> newRepresentations) {
        fieldRepresentationRepository.deleteAllByBlog(blog);

        return fieldRepresentationRepository.saveAll(newRepresentations);
    }

    public List<ApplicationBlogFieldRepresentation> saveAll(List<ApplicationBlogFieldRepresentation> fields) {
        return fieldRepresentationRepository.saveAll(fields);
    }
}
