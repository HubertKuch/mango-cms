package com.hubert.mangocms.domain.models.app;

import com.hubert.mangocms.domain.models.blog.Blog;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
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
@Entity(name = "blogs")
@RequiredArgsConstructor
public class ApplicationFieldDefinition {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private boolean isRequired;
    private FieldType type;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Application application;

    public ApplicationFieldDefinition(String name, boolean isRequired, FieldType type, Application application) {
        this.name = name;
        this.isRequired = isRequired;
        this.type = type;
        this.application = application;
    }
}
