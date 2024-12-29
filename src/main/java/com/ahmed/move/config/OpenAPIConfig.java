package com.ahmed.move.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition
public class OpenAPIConfig {

        @Bean
        public OpenAPI baseOpenAPI() {
            return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info()
                    .title("Fawry Associate software engineer development task {Spring Doc}")
                    .version("1.0.0").description("Spring doc"));

        }
    }




