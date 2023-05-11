package com.hubert.mangocms.controllers.user;

import com.hubert.mangocms.domain.exceptions.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.user.UserExistsException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import com.hubert.mangocms.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
final public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register/")
    public User register(@RequestBody UserRegister userRegister) throws UserExistsException, InvalidRequestException {
        return userService.register(userRegister);
    }

}
