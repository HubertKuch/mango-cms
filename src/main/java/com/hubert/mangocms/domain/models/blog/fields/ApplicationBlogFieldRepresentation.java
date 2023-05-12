package com.hubert.mangocms.domain.models.blog.fields;

import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.Blog;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "blog_field_representation")
public class ApplicationBlogFieldRepresentation {
    @Id
    private String id = UUID.randomUUID().toString();
    private String value;
    @ManyToOne
    @JoinColumn(name = "field_definition_id")
    private ApplicationFieldDefinition definition;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public ApplicationBlogFieldRepresentation(String value, ApplicationFieldDefinition definition, Blog blog) {
        this.value = value;
        this.definition = definition;
    }
}
