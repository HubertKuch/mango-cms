package com.hubert.mangocms.services.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    JwtService jwtService;

    @BeforeEach
    void setUp() {
        this.jwtService = new JwtService("test", "Mango", Duration.ofDays(2));
    }

    @Test
    void tokenize() {
        String token = jwtService.tokenize("id", UUID.randomUUID().toString());

        assertAll(
                () -> assertNotNull(token),
                () -> assertEquals(3, token.split("\\.").length)
        );
    }

    @Test
    void givenValidToken_shouldReturnValidDecoded() {
        String token = jwtService.tokenize("id", UUID.randomUUID().toString());
        DecodedJWT decodedJWT = jwtService.decode(token);

        assertAll(
                () -> assertNotNull(decodedJWT.getClaim("id").asString())
        );
    }

    @Test
    void givenExpiredToken_shouldReturnNull() {
        JwtService expiredService = new JwtService("test", "Mango", Duration.ofMinutes(-2));
        String token = expiredService.tokenize("id", UUID.randomUUID().toString());

        DecodedJWT decodedJWT = expiredService.decode(token);

        assertAll(
                () -> assertNull(decodedJWT)
        );
    }
}