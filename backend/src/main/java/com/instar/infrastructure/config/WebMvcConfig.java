package com.instar.infrastructure.config;

import com.instar.infrastructure.interceptor.HttpLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpLoggingInterceptor httpLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLoggingInterceptor)
                .excludePathPatterns("/actuator/health", "/swagger/**", "/v3/api-docs/**",
                        "/static/**", "/webjars/**");
    }
}

