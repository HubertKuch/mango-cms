package com.hubert.mangocms.domain.models.app;

import com.hubert.mangocms.domain.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "applications")
@AllArgsConstructor
public class Application {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private Timestamp createdAt;
    @ManyToOne
    private User user;
    @OneToOne
    @JoinColumn(name = "id")
    private ApplicationKeys keys;
}
