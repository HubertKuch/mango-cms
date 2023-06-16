package com.hubert.mangocms.configuration.argument.resolvers;

import com.hubert.mangocms.domain.argumentresolvers.ApplicationArgumentResolver;
import com.hubert.mangocms.middleware.UserApplicationMiddleware;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public record ApplicationArgumentResolverConfiguration(
        UserApplicationMiddleware userApplicationMiddleware
) implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ApplicationArgumentResolver(userApplicationMiddleware));
    }
}
