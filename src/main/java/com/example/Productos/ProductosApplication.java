package com.example.Productos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Microservicio de Productos API",
				version = "1.0",
				description = "API para la gestión de productos en el sistema de inventario.",
				contact = @Contact(
						name = "Anderson Ramirez Alejo",
						email = "anderson.ramirez@linktic.com"
				)
		),
		tags = {
				@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
		}
)
@SecurityScheme(
		name = "api_key",
		type = SecuritySchemeType.APIKEY,
		in = SecuritySchemeIn.HEADER,
		paramName = "X-API-KEY",
		description = "API Key para la autenticación de solicitudes"
)
public class ProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductosApplication.class, args);
	}

}
