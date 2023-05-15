package com.hubert.mangocms.configuration;

import com.hubert.mangocms.interceptors.RestrictedAnnotationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final RestrictedAnnotationInterceptor restrictedAnnotationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restrictedAnnotationInterceptor).addPathPatterns("/**").order(1);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
