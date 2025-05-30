package com.example.Productos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación para validar la API Key en las solicitudes HTTP.
 * <p>
 * Este filtro intercepta cada solicitud HTTP, extrae la API Key de la cabecera especificada
 * y delega la autenticación al AuthenticationManager. Si la autenticación falla, responde con 401.
 * </p>
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String authenticationHeaderName;
    private final AuthenticationManager authenticationManager;


    /**
     * Constructor del filtro.
     *
     * @param authenticationHeaderName Nombre de la cabecera HTTP donde se espera la API Key.
     * @param authenticationManager    AuthenticationManager para validar la API Key.
     */
    public ApiKeyAuthFilter(String authenticationHeaderName, AuthenticationManager authenticationManager) {
        this.authenticationHeaderName = authenticationHeaderName;
        this.authenticationManager = authenticationManager;
    }


    /**
     * Lógica principal del filtro. Extrae la API Key de la cabecera, intenta autenticarla
     * y establece el contexto de seguridad si es válida. Si falla, responde con 401.
     *
     * @param request     Solicitud HTTP entrante.
     * @param response    Respuesta HTTP.
     * @param filterChain Cadena de filtros de Spring.
     * @throws ServletException en caso de error de filtro.
     * @throws IOException      en caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String apiKey = request.getHeader(authenticationHeaderName);

        if (apiKey == null || apiKey.isEmpty()) {
            // No hay API Key, permitir que Spring Security la maneje (Denegar acceso)
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = new ApiKeyAuthToken(apiKey, null);
            Authentication result = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(result);
        } catch (Exception e) {
            // Si la autenticación falla, limpiar el contexto y enviar error 401
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso no autorizado: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
