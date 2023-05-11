package com.hubert.mangocms.controllers.user;

import com.hubert.mangocms.domain.exceptions.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.user.UserExistsException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import com.hubert.mangocms.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register/")
    public ResponseEntity<?> register(@RequestBody UserRegister userRegister) throws
            UserExistsException,
            InvalidRequestException {
        User user = userService.register(userRegister);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
