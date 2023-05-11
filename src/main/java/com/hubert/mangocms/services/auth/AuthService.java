package com.hubert.mangocms.services.auth;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hubert.mangocms.configuration.AuthConfiguration;
import com.hubert.mangocms.domain.exceptions.auth.TokenExpiredException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.models.user.claims.UserClaims;
import com.hubert.mangocms.services.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthConfiguration authConfiguration;

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

    public Cookie getAuthCookie(String token) {
        Cookie cookie = new Cookie(authConfiguration.getCookie().getName(), token);

        cookie.setMaxAge((int) authConfiguration.getCookie().getAge().toSeconds());
        cookie.setPath("/");
        cookie.setSecure(authConfiguration.getCookie().isSecure());
        cookie.setHttpOnly(authConfiguration.getCookie().isHttpOnly());

        return cookie;
    }
}
