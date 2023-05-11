package com.hubert.mangocms.services.user;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.user.UserExistsException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import com.hubert.mangocms.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
final public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

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
