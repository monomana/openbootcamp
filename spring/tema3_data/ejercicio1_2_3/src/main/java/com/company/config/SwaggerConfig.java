package com.company.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

    /**
     *
     * HTML: http://localhost:8080/swagger-ui/
     * JSON: http://localhost:8080/v2/api-docs
     */
    @Configuration
    public class SwaggerConfig {

        @Bean
        public Docket api() {

            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiDetails())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
        }

        private ApiInfo apiDetails() {
            return new ApiInfo("Spring Boot Computer Service API",
                    "Computer - API Swagger documentation",
                    "1.0.0",
                    "https://www.github.com/",
                    new Contact("Alan", "https://www.github.com/", "nasatest@example.com"),
                    "MIT",
                    "https://www.github.com/",
                    Collections.emptyList());
        }
    }