package com.example.humansvszombiesbackend.security.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerBeanConfig {

    @Bean
    ApiInfoProvider apiInfoProvider(SwaggerProperties swaggerProperties) {
        return new ApiInfoProvider(swaggerProperties);
    }
}