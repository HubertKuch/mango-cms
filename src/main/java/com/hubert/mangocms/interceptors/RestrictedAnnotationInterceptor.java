package com.hubert.mangocms.interceptors;

import com.hubert.mangocms.configuration.AuthConfiguration;
import com.hubert.mangocms.domain.annotations.Restricted;
import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.domain.models.user.claims.UserClaims;
import com.hubert.mangocms.services.auth.AuthService;
import com.hubert.mangocms.services.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
public class RestrictedAnnotationInterceptor extends WebRequestHandlerInterceptorAdapter {
    private final AuthConfiguration authConfiguration;
    private final AuthService authService;
    private final UserService userService;

    public RestrictedAnnotationInterceptor(
            WebRequestInterceptor requestInterceptor,
            AuthConfiguration authConfiguration,
            AuthService authService,
            UserService userService
    ) {
        super(requestInterceptor);
        this.authConfiguration = authConfiguration;
        this.authService = authService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (handler instanceof HandlerMethod handlerMethod && !(handlerMethod.hasMethodAnnotation(Restricted.class))) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Cookie cookie = WebUtils.getCookie(request, authConfiguration.getCookie().getName());

        if (cookie == null) {
            throw new AuthenticationException("Missing authentication cookie.");
        }

        String token = cookie.getValue();
        UserClaims claims = authService.decode(token);
        String userId = claims.id();
        User user = userService.findById(userId).orElseThrow(() -> {
            log.error("Token was interrupted: User id: {}", userId);
            return new AuthenticationException("Cannot authenticate");
        });

        request.setAttribute("user", user);

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex
    ) throws Exception {
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView
    ) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
