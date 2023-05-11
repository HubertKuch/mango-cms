package com.hubert.mangocms.domain.models.blog.fields;

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
@Entity(name = "blogs")
@RequiredArgsConstructor
public class BlogFieldDefinition {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private FieldType type;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    public BlogFieldDefinition(String name, FieldType type, Blog blog) {
        this.name = name;
        this.type = type;
        this.blog = blog;
    }
}
