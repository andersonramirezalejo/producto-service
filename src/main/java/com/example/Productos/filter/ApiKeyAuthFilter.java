package com.example.Productos.filter; // Or wherever your filter is

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String apiKeyHeaderName;
    private final String expectedApiKey;

    // Paths to exclude from API Key authentication
    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
            "/swagger-ui.html",
            "/swagger-ui/", // Important for the root of swagger-ui
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/error" // Spring Boot error page
    );

    public ApiKeyAuthFilter(String apiKeyHeaderName, String expectedApiKey) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.expectedApiKey = expectedApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // Check if the current request URI starts with any of the excluded paths
        boolean isExcluded = EXCLUDE_PATHS.stream().anyMatch(path -> {
            if (path.endsWith("/**")) {
                return requestUri.startsWith(path.substring(0, path.length() - 3)); // Check prefix for /**
            }
            return requestUri.equals(path); // Exact match
        });

        if (isExcluded) {
            filterChain.doFilter(request, response); // Bypass authentication for excluded paths
            return;
        }

        // Existing API Key authentication logic
        String apiKey = request.getHeader(apiKeyHeaderName);

        if (apiKey == null || !apiKey.equals(expectedApiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Unauthorized: Missing or Invalid API Key");
            return;
        }

        filterChain.doFilter(request, response);
    }
}