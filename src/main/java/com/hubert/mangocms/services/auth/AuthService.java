package com.hubert.mangocms.services.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hubert.mangocms.domain.exceptions.auth.TokenExpiredException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.models.user.claims.UserClaims;
import com.hubert.mangocms.services.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    public String tokenize(User user) {
        return jwtService.tokenize("id", user.getId());
    }

    public UserClaims decode(String token) throws TokenExpiredException {
        DecodedJWT decodedJWT = jwtService.decode(token);

        if (decodedJWT == null) {
            throw new TokenExpiredException();
        }

        Claim id = decodedJWT.getClaim("id");

        return new UserClaims(id.asString());
    }
}
