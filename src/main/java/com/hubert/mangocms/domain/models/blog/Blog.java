package com.hubert.mangocms.domain.models.blog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "blogs")
@AllArgsConstructor
public class Blog {
    @Id
    private String id = UUID.randomUUID().toString();
    @ManyToOne
    @JoinColumn(name = "application_id")
    @JsonIgnore
    private Application application;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<ApplicationBlogFieldRepresentation> fields;

    public Blog(Application application) {
        this.application = application;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public void addField(ApplicationBlogFieldRepresentation representation) {
        fields.add(representation);
    }
}
