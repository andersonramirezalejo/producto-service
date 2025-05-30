package com.example.Productos.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * AuthenticationManager personalizado para validar la API Key.
 * <p>
 * Este manager compara la API Key recibida en la autenticación con la clave esperada.
 * Si la clave es válida, autentica el token; de lo contrario, lanza una excepción.
 * </p>
 */
public class ApiKeyAuthManager implements AuthenticationManager {

    private final String principalRequestHeader;
    
    public ApiKeyAuthManager(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }


    /**
     * Valida la API Key recibida en el token de autenticación.
     *
     * @param authentication Token de autenticación recibido.
     * @return Token autenticado si la API Key es válida, se autentica el token
     * @throws AuthenticationException Si la API Key es inválida.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = authentication.getPrincipal().toString();
        if (!apiKey.equals(principalRequestHeader)) {
            throw new BadCredentialsException("API Key inválida");
        }
        return new ApiKeyAuthToken(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
