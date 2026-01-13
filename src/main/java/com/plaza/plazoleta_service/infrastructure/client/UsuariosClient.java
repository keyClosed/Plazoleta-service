package com.plaza.plazoleta_service.infrastructure.client;

import com.plaza.plazoleta_service.application.dto.response.UsuarioResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class UsuariosClient {

    private final RestTemplate restTemplate;

    public UsuariosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioResponse obtenerClientePorId(Long id, String token) {
        try {
            String url = "http://localhost:8080/clientes/" + id;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<UsuarioResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET, entity, UsuarioResponse.class);

            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (HttpClientErrorException e) {
            System.err.println("Error al obtener cliente: " + e.getMessage());
            return null;
        }
    }
}