package com.example.Productos.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

/**
 * Token de autenticación personalizado para API Key.
 * <p>
 * Representa un token de autenticación basado en una API Key, utilizado por Spring Security.
 * </p>
 */
public class ApiKeyAuthToken extends AbstractAuthenticationToken {

    private final String apiKey;
    
    public ApiKeyAuthToken(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }
    
    @Override
    public Object getCredentials() {
        return null;
    }
    
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}
