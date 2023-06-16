package com.hubert.mangocms.domain.argumentresolvers;

import com.hubert.mangocms.domain.models.app.Application;
import com.hubert.mangocms.domain.models.user.User;
import com.hubert.mangocms.middleware.UserApplicationMiddleware;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

@Slf4j(topic = "APPLICATION_RESOLVER")
public record ApplicationArgumentResolver(
        UserApplicationMiddleware userApplicationMiddleware
) implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == Application.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            log.error("Request is null");
            return null;
        }

        Map<String, String> variables = getPathVariables(request);

        Optional<String> applicationId = Optional.ofNullable(variables.get("applicationId"));

        if (applicationId.isEmpty()) {
            return null;
        }

        User user = (User) request.getAttribute("user");

        return userApplicationMiddleware.userMustBeOwnerById(user, applicationId.get());
    }

    public Map<String, String> getPathVariables(HttpServletRequest request) {
        return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
