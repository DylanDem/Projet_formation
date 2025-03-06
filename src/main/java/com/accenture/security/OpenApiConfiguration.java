package com.accenture.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI apiConfiguration() {
        return new OpenAPI().info(new Info()
                .title("Vehicle's rental")
                .description("Rental's car api")
                .version("0.0.1")
                .contact(new Contact()
                        .name("DEMASSE Dylan")
                        .email("dylan.demasse@accenture.com")));
    }
}
