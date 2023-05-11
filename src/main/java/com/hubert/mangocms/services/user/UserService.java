package com.hubert.mangocms.services.user;

import com.hubert.mangocms.domain.exceptions.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.user.UserExistsException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import com.hubert.mangocms.domain.responses.BaseResponse;
import com.hubert.mangocms.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(UserRegister userRegister) throws UserExistsException, InvalidRequestException {
        if (userRepository.existsByUsername(userRegister.username())) {
            throw new UserExistsException("User with that username exists");
        }

        if (!userRegister.password().password().equals(userRegister.password().repeatPassword())) {
            throw new InvalidRequestException("Passwords aren't same");
        }

        User user = User.register(userRegister);

        userRepository.save(user);

        return user;
    }
}
