package com.example.Productos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI para la documentación de la API de Productos.
 * <p>
 * Esta clase define la información básica de la API y el esquema de seguridad
 * basado en una API Key enviada en el header "X-API-KEY".
 * </p>
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "API de Productos", version = "1.0", description = "Documentación de la API de Microservicio de Productos"),
        security = @SecurityRequirement(name = "X-API-KEY")
)
@SecurityScheme(
        name = "X-API-KEY",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-KEY",
        description = "Requiere una X-API-KEY para acceder a los endpoints."
)
public class OpenApiConfig {    
}
