package com.ahmed.move;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Fawry Code Test",
                version = "1.0",
                description = "Fawry Code Test"
        )
)
public class MovieManagementAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieManagementAppApplication.class, args);
    }

}
