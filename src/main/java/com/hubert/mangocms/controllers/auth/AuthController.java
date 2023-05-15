package com.hubert.mangocms.controllers.auth;

import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.auth.LoginCredentials;
import com.hubert.mangocms.domain.responses.BaseResponse;
import com.hubert.mangocms.services.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
final public class AuthController {

    private final AuthService authService;

    @Restricted
    @GetMapping("/")
    public User user(@RequestAttribute User user) {
        return user;
    }

    @PostMapping("/")
    public BaseResponse baseResponse(
            @RequestBody LoginCredentials loginCredentials, HttpServletResponse response
    ) throws InvalidRequestException {
        Cookie authCookie = authService.login(loginCredentials);

        response.addCookie(authCookie);

        return new BaseResponse("Logged in");
    }
}
