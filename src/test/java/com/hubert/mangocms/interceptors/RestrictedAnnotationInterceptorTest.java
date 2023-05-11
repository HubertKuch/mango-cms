package com.hubert.mangocms.interceptors;

import com.hubert.mangocms.configuration.AuthConfiguration;
import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.repositories.user.UserRepository;
import com.hubert.mangocms.services.auth.AuthService;
import com.hubert.mangocms.services.jwt.JwtService;
import com.hubert.mangocms.services.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.HandlerMethod;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RestrictedAnnotationInterceptorTest {

    @Autowired
    AuthConfiguration authConfiguration;
    RestrictedAnnotationInterceptor restrictedAnnotationInterceptor;
    UserRepository userRepository;
    AuthService authService;

    @BeforeEach
    void setUp() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        AuthService authService = new AuthService(new JwtService("test", "mango", Duration.ofDays(2)),
                authConfiguration,
                new UserService(mock(UserRepository.class))
        );

        this.userRepository = userRepository;
        this.authService = authService;

        restrictedAnnotationInterceptor = new RestrictedAnnotationInterceptor(mock(WebRequestInterceptor.class),
                authConfiguration,
                authService,
                userService
        );
    }

    @Test
    void givenValidUserId_thenAuthenticate_shouldReturnTrue() throws Exception {
        User user = new User("test", "test");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HandlerMethod handlerMethod = mock(HandlerMethod.class);

        when(handlerMethod.hasMethodAnnotation(Restricted.class)).thenReturn(true);
        String token = authService.tokenize(user);
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{new Cookie("token", token)});

        boolean result = restrictedAnnotationInterceptor.preHandle(httpServletRequest,
                mock(HttpServletResponse.class),
                handlerMethod
        );

        assertTrue(result);
    }

    @Test
    void givenNotValidUserId_thenAuthenticate_shouldThrowException() throws Exception {
        User user = new User("test", "test");
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HandlerMethod handlerMethod = mock(HandlerMethod.class);

        when(handlerMethod.hasMethodAnnotation(Restricted.class)).thenReturn(true);
        String token = authService.tokenize(user);
        when(httpServletRequest.getCookies()).thenReturn(new Cookie[]{new Cookie("token", token)});

        assertThrows(AuthenticationException.class,
                () -> restrictedAnnotationInterceptor.preHandle(httpServletRequest,
                        mock(HttpServletResponse.class),
                        handlerMethod
                )
        );
    }

    @Test
    void givenNotRestrictedRoute_shouldPassTrue() throws Exception {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HandlerMethod handlerMethod = mock(HandlerMethod.class);

        when(handlerMethod.hasMethodAnnotation(Restricted.class)).thenReturn(false);

        assertTrue(restrictedAnnotationInterceptor.preHandle(httpServletRequest,
                mock(HttpServletResponse.class),
                handlerMethod
        ));
    }
}