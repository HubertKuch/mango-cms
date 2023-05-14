package com.hubert.mangocms.services.blog;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.mappers.FieldRepresentationMapper;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.requests.blog.CreateBlog;
import com.hubert.mangocms.repositories.blog.BlogRepository;
import com.hubert.mangocms.services.application.ApplicationBlogFieldRepresentationService;
import com.hubert.mangocms.services.application.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final ApplicationBlogFieldRepresentationService applicationBlogFieldRepresentationService;
    private final ApplicationService applicationService;
    private final FieldRepresentationMapper fieldRepresentationMapper;

    public Optional<Blog> findById(String id) {
        return blogRepository.findById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public Blog createBlog(String applicationId, CreateBlog createBlog) throws
            InvalidRequestException {
        Application application = applicationService
                .findById(applicationId)
                .orElseThrow(() -> new InvalidRequestException("Invalid application id"));
        Blog blog = new Blog(application);
        List<ApplicationBlogFieldRepresentation> fields = fieldRepresentationMapper.fromCredentials(blog,
                createBlog.fields()
        );

        save(blog);
        applicationBlogFieldRepresentationService.saveAll(fields);

        return blog;
    }

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }
}
