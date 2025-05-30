package com.example.Productos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API de Productos", version = "1.0", description = "Documentación de la API de Microservicio de Productos"),
        security = @SecurityRequirement(name = "X-API-KEY") // Aplicar globalmente para todos los endpoints
)
@SecurityScheme(
        name = "X-API-KEY",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-KEY",
        description = "Requiere una X-API-KEY para acceder a los endpoints."
)
public class OpenApiConfig {
    // No se necesita lógica adicional en el bean
}
