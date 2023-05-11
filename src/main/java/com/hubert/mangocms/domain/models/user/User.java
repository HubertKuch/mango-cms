package com.hubert.mangocms.domain.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity(name = "users")
@AllArgsConstructor
public class User {
    @Id
    private String id = UUID.randomUUID().toString();
    private String username;
    @JsonIgnore
    private String passwordHash;
    private Timestamp registeredAt;
    private Timestamp updatedAt;

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.registeredAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    public boolean validPassword(String password) {
        return hash(password).equals(passwordHash);
    }

    public static User register(UserRegister userRegister) {
        return new User(userRegister.username(), hash(userRegister.password().password()));
    }

    public static String hash(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
