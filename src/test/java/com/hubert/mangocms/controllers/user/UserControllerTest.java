package com.hubert.mangocms.controllers.user;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.exceptions.user.UserExistsException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.requests.users.UserRegister;
import com.hubert.mangocms.domain.requests.users.credentials.RepeatPasswordCredentials;
import com.hubert.mangocms.repositories.user.UserRepository;
import com.hubert.mangocms.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    UserRepository userRepository;
    UserService userService;
    UserController userController;

    @BeforeEach
    void setUp() {
        this.userRepository = mock(UserRepository.class);
        this.userService = new UserService(userRepository);
        this.userController = new UserController(userService);
    }

    @Test
    void givenValidData_thenRegister_shouldReturnValidUserObject() throws UserExistsException, InvalidRequestException {
        String username = "username";
        String password = "password";

        UserRegister userRegister = new UserRegister(username, new RepeatPasswordCredentials(password, password));

        when(userRepository.existsByUsername(username)).thenReturn(false);

        User user = userController.register(userRegister);

        assertAll(
                () -> assertNotNull(user.getRegisteredAt()),
                () -> assertNotNull(user.getUsername()),
                () -> assertNotNull(user.getPasswordHash()),
                () -> assertNotEquals(user.getPasswordHash(), password)
        );
    }

    @Test
    void givenDifferentPassword_thenRegister_shouldThrowException() {
        String username = "username";

        UserRegister userRegister = new UserRegister(username, new RepeatPasswordCredentials("test1", "Test2"));

        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> userController.register(userRegister));
    }

    @Test
    void givenBusyUsername_thenRegister_shouldThrowException() {
        String username = "username";

        UserRegister userRegister = new UserRegister(username, new RepeatPasswordCredentials("test1", "test1"));

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(UserExistsException.class, () -> userController.register(userRegister));
    }
}