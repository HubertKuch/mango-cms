package com.hubert.mangocms.domain.models.app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubert.mangocms.domain.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "applications")
public class Application {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private Timestamp createdAt;
    @ManyToOne
    @JsonIgnore
    private User user;
    @MapsId
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", referencedColumnName = "application_id")
    private ApplicationKeys keys;

    public Application(String name, User user) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.user = user;
        this.keys = ApplicationKeys.from(this);
    }
}
