package com.example.Productos.filter; 

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filtro de autenticación para validar la API Key en las solicitudes HTTP.
 * <p>
 * Este filtro verifica que todas las solicitudes (excepto las rutas excluidas)
 * incluyan la cabecera especificada con la API Key correcta. Si la clave es inválida
 * o falta, la solicitud es rechazada con un error 401 (Unauthorized).
 * </p>
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String apiKeyHeaderName;
    private final String expectedApiKey;
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/swagger-ui.html",
            "/swagger-ui/", 
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/error" 
    );

    /**
     * Constructor del filtro.
     *
     * @param apiKeyHeaderName Nombre de la cabecera HTTP donde se espera la API Key.
     * @param expectedApiKey   Valor esperado de la API Key.
     */
    public ApiKeyAuthFilter(String apiKeyHeaderName, String expectedApiKey) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.expectedApiKey = expectedApiKey;
    }

    /**
     * Lógica principal del filtro. Valida la API Key en las solicitudes entrantes,
     * permitiendo el paso solo si es correcta o si la ruta está excluida.
     *
     * @param request     Solicitud HTTP entrante.
     * @param response    Respuesta HTTP.
     * @param filterChain Cadena de filtros de Spring.
     * @throws ServletException en caso de error de filtro.
     * @throws IOException      en caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();
        boolean isExcluded = EXCLUDE_PATHS.stream().anyMatch(path -> {
            if (path.endsWith("/**")) {
                return requestUri.startsWith(path.substring(0, path.length() - 3)); 
            }
            return requestUri.equals(path); 
        });

        if (isExcluded) {
            filterChain.doFilter(request, response); 
            return;
        }

        
        String apiKey = request.getHeader(apiKeyHeaderName);

        if (apiKey == null || !apiKey.equals(expectedApiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("No autorizado: Falta o clave de API no válida");
            return;
        }

        filterChain.doFilter(request, response);
    }
}