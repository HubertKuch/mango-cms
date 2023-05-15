package com.hubert.mangocms.services.blog;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.mappers.FieldRepresentationMapper;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.blog.CreateBlog;
import com.hubert.mangocms.domain.requests.blog.UpdateBlog;
import com.hubert.mangocms.repositories.blog.BlogRepository;
import com.hubert.mangocms.services.application.ApplicationBlogFieldRepresentationService;
import com.hubert.mangocms.services.application.ApplicationFieldDefinitionService;
import com.hubert.mangocms.services.application.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final ApplicationBlogFieldRepresentationService applicationBlogFieldRepresentationService;
    private final ApplicationService applicationService;
    private final FieldRepresentationMapper fieldRepresentationMapper;
    private final ApplicationFieldDefinitionService applicationFieldDefinitionService;

    public Optional<Blog> findById(String id) {
        return blogRepository.findById(id);
    }

    public List<Blog> findByApplicationId(String applicationId) {
        return blogRepository.findALlByApplication_Id(applicationId);
    }

    @Transactional(rollbackOn = Throwable.class)
    public Blog createBlog(User user, String applicationId, CreateBlog createBlog) throws
            InvalidRequestException,
            ConflictException {
        Application application = applicationService.findApplicationOfUser(user, applicationId);
        Blog blog = new Blog(application);

        List<ApplicationBlogFieldRepresentation> fields = fieldRepresentationMapper.fromCredentials(blog,
                createBlog.fields()
        );
        List<ApplicationFieldDefinition> fieldDefinitions = fields.stream().map(ApplicationBlogFieldRepresentation::getDefinition).toList();
        List<ApplicationFieldDefinition> requiredFields = applicationFieldDefinitionService.findRequiredByApplication(application);

        boolean allRequiredFieldFilled = new HashSet<>(fieldDefinitions).containsAll(requiredFields);

        if (!allRequiredFieldFilled) {
            throw new ConflictException("All required fields must be filled");
        }

        save(blog);
        applicationBlogFieldRepresentationService.saveAll(fields);

        return blog;
    }

    public Blog update(User user, String applicationId, String blogId, UpdateBlog updateBlog) throws InvalidRequestException {
        applicationService.findApplicationOfUser(user, applicationId);

        Blog blog = findById(blogId).orElseThrow(() -> new InvalidRequestException("Invalid blog id"));
        List<ApplicationBlogFieldRepresentation> representations = fieldRepresentationMapper.fromCredentials(blog, updateBlog.fields());

        applicationBlogFieldRepresentationService.replaceWith(blog, representations);

        return blog;
    }

    @Transactional(rollbackOn = Throwable.class)
    public void delete(User owner, String applicationId, String blogId) throws InvalidRequestException {
        applicationService.findApplicationOfUser(owner, applicationId);
        blogRepository.deleteById(blogId);
    }

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }
}
