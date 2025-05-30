package com.example.Productos.config;

import com.example.Productos.security.ApiKeyAuthFilter;
import com.example.Productos.security.ApiKeyAuthManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${service.api.key}")
    private String productsApiKey;

    private static final String API_KEY_AUTH_HEADER = "X-API-KEY";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Deshabilitar CSRF (común para APIs REST)
        http.csrf(csrf -> csrf.disable())
                // Configurar manejo de excepciones
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Acceso no autorizado: Autenticación requerida.");
                        })
                )
                // Configurar manejo de sesión sin estado (REST)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Añadir nuestro filtro de autenticación API Key
                .addFilterBefore(new ApiKeyAuthFilter(API_KEY_AUTH_HEADER, new ApiKeyAuthManager(productsApiKey)),
                        UsernamePasswordAuthenticationFilter.class)
                // Autorización de solicitudes
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a la documentación de Swagger UI sin autenticación
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                        // Proteger todos los endpoints de /api/productos con nuestra API Key
                        .requestMatchers("/api/productos/**").authenticated()
                        // Denegar el acceso al resto de las solicitudes por defecto
                        .anyRequest().denyAll()
                );

        return http.build();
    }
}
