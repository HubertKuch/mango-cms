package com.hubert.mangocms.services.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    private final Algorithm algorithm;
    private final String issuer;
    private final Duration expiringDuration;
    private final JWTVerifier jwtVerifier;

    public JwtService(
            @Value("jwt.secret") String secret,
            @Value("jwt.issuer") String issuer,
            @Value("jwt.expiringDuration") Duration expiringDuration
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.expiringDuration = expiringDuration;
        this.jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String tokenize(String claimName, String value) {
        return JWT.create()
                  .withIssuer(issuer)
                  .withSubject("auth_token")
                  .withClaim(claimName, value)
                  .withIssuedAt(new Date())
                  .withExpiresAt(new Date(System.currentTimeMillis() + expiringDuration.toMillis()))
                  .withJWTId(UUID.randomUUID().toString())
                  .sign(algorithm);
    }

    public DecodedJWT decode(String jwt) {
        try {
            return jwtVerifier.verify(jwt);
        } catch (Throwable throwable) {
            log.error("JWT Exception", throwable);
            return null;
        }
    }

}
