package com.example.Productos.integration;

import com.example.Productos.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductoIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String API_KEY = "productos_secreta_12345";

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/productos";
    }

    @Test
    void whenGetAllProductosWithApiKey_thenStatus200() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Producto[]> response = restTemplate.exchange(
                getBaseUrl(),
                HttpMethod.GET,
                entity,
                Producto[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenGetAllProductosWithoutApiKey_thenStatus401() {
        ResponseEntity<String> response = restTemplate.getForEntity(getBaseUrl(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
