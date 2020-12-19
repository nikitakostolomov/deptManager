package com.deptManager.deptManager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.deptManager.deptManager.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    public static final String[] AUTH_WHITELIST = {
            "/swagger-ui*/**",
            "/webjars/springfox-swagger-ui*/**",
            "/v2*/**",
            "/swagger-resources*/**",
            "/favicon.ico",
            "/**",
    };
}