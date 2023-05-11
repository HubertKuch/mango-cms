package com.hubert.mangocms.services.auth;

import com.hubert.mangocms.configuration.AuthConfiguration;
import com.hubert.mangocms.domain.exceptions.auth.TokenExpiredException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.services.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    JwtService jwtService;
    AuthService authService;

    @BeforeEach
    void setUp() {
        this.jwtService = new JwtService("test", "Mango", Duration.ofDays(2));
        this.authService = new AuthService(jwtService, new AuthConfiguration());
    }

    @Test
    void givenValidToken_shouldNotThrowException() {
        assertDoesNotThrow(() -> {
            authService.tokenize(new User(UUID.randomUUID().toString(), "test", "test", null, null));
        });
    }

    @Test
    void givenExpiredToken_shouldThrowException() {
        this.authService = new AuthService(new JwtService("test", "test", Duration.ofDays(-1)), new AuthConfiguration());

        String token = authService.tokenize(new User(UUID.randomUUID().toString(), "test", "test", null, null));

        assertThrows(TokenExpiredException.class, () -> authService.decode(token));
    }
}