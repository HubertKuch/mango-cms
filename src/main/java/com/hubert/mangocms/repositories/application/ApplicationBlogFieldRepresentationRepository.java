package com.hubert.mangocms.repositories.application;

import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationBlogFieldRepresentationRepository extends JpaRepository<ApplicationBlogFieldRepresentation, String> {
    List<ApplicationBlogFieldRepresentation> findAllByBlog(Blog blog);
    void deleteAllByBlog(Blog blog);
}
