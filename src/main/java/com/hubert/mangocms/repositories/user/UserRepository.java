package com.hubert.mangocms.repositories.user;

import com.hubert.mangocms.domain.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
