package com.teum.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI teumOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Teum E-commerce API")
                .description("API documentation for Teum cross-border e-commerce platform")
                .version("1.0.0"));
    }
}
