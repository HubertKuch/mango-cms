package com.hubert.mangocms.domain.argumentresolvers;

import com.hubert.mangocms.domain.exceptions.internal.InvalidRequestException;
import com.hubert.mangocms.domain.models.entities.EntityModel;
import com.hubert.mangocms.services.entities.EntityModelService;
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

@Slf4j(topic = "ENTITY_MODEL_RESOLVER")
public record EntityModelArgumentResolver(
        EntityModelService entityModelService
) implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType() == EntityModel.class;
    }

    @Override
    public EntityModel resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            log.error("Request is null");
            return null;
        }

        Map<String, String> variables = getPathVariables(request);
        Optional<String> entityModelId = Optional.ofNullable(variables.get("entityModelId"));

        return entityModelId.map(entityModelService::findById).orElseThrow(() -> new InvalidRequestException("Invalid entity model id")).get();
    }

    public Map<String, String> getPathVariables(HttpServletRequest request) {
        return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    }
}
