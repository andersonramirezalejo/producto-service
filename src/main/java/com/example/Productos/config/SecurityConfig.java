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

/**
 * Configuración de seguridad para el microservicio de productos.
 * <p>
 * Esta clase configura la seguridad basada en API Key para los endpoints de productos,
 * permitiendo el acceso solo a quienes incluyan la cabecera "X-API-KEY" con el valor correcto.
 * Además, expone la documentación de Swagger sin autenticación.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
     
    @Value("${service.api.key}")
    private String productsApiKey;
    
    private static final String API_KEY_AUTH_HEADER = "X-API-KEY";

    /**
     * Configura la cadena de filtros de seguridad para la aplicación.
     *
     * <ul>
     *   <li>Deshabilita CSRF.</li>
     *   <li>Configura el manejo de excepciones para respuestas no autorizadas.</li>
     *   <li>Establece la política de sesión como stateless.</li>
     *   <li>Agrega un filtro personalizado para validar la API Key.</li>
     *   <li>Permite el acceso público a la documentación Swagger.</li>
     *   <li>Requiere autenticación para los endpoints de productos.</li>
     *   <li>Deniega cualquier otro acceso.</li>
     * </ul>
     *
     * @param http objeto de configuración de seguridad HTTP
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception en caso de error de configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Acceso no autorizado: Autenticación requerida.");
                        })
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ApiKeyAuthFilter(API_KEY_AUTH_HEADER, new ApiKeyAuthManager(productsApiKey)),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**").permitAll()
                        .requestMatchers("/api/productos/**").authenticated()
                        .anyRequest().denyAll()
                );

        return http.build();
    }
}
