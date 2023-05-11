package com.hubert.mangocms.services.auth;

import com.hubert.mangocms.configuration.AuthConfiguration;
import com.hubert.mangocms.domain.exceptions.auth.TokenExpiredException;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.auth.LoginCredentials;
import com.hubert.mangocms.repositories.user.UserRepository;
import com.hubert.mangocms.services.jwt.JwtService;
import com.hubert.mangocms.services.user.UserService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    JwtService jwtService;
    AuthService authService;
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.jwtService = new JwtService("test", "Mango", Duration.ofDays(2));
        this.userRepository = mock(UserRepository.class);
        AuthConfiguration authConfiguration = new AuthConfiguration();
        AuthConfiguration.AuthCookieConfiguration cookieConfiguration = new AuthConfiguration.AuthCookieConfiguration();

        authConfiguration.setCookie(cookieConfiguration);

        this.authService = new AuthService(jwtService, authConfiguration, new UserService(userRepository));
    }

    @Test
    void givenValidToken_shouldNotThrowException() {
        assertDoesNotThrow(() -> {
            authService.tokenize(new User(UUID.randomUUID().toString(), "test", "test", null, null));
        });
    }

    @Test
    void givenExpiredToken_shouldThrowException() {
        this.authService = new AuthService(new JwtService("test", "test", Duration.ofDays(-1)),
                new AuthConfiguration(),
                new UserService(mock(UserRepository.class))
        );

        String token = authService.tokenize(new User(UUID.randomUUID().toString(), "test", "test", null, null));

        assertThrows(TokenExpiredException.class, () -> authService.decode(token));
    }

    @Test
    void givenValidLoginCredentials_shouldPassAndReturnCookie() throws InvalidRequestException {
        User user = new User(UUID.randomUUID().toString(), "", User.hash("test"), null, null);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        Cookie cookie = authService.login(new LoginCredentials("test", "test"));

        assertAll(
                () -> assertNotNull(cookie)
        );
    }

    @Test
    void givenNotValidLoginCredentials_shouldThrowException() throws InvalidRequestException {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(InvalidRequestException.class, () -> authService.login(new LoginCredentials("test", "test")))
        );
    }
}