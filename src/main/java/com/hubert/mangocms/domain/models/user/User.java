package com.hubert.mangocms.domain.models.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "users")
@RequiredArgsConstructor
public class User {
    @Id
    private String id = UUID.randomUUID().toString();
    private String username;
    private String passwordHash;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
}
