package com.urilvv.GymApplicationEpam.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Yurii Danko",
                        email = "urilvv55@gmail.com",
                        url = "https://epam.com"
                ),
                description = "OpenAPI documentation for GymApplicationEpam",
                title = "OpenAPI Docs Epam",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local environment",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
