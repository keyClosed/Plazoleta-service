package com.plaza.plazoleta_service.infrastructure.client;

import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

@Component
public class TrazabilidadClient implements ITrazabilidadClientPort {

    private final RestTemplate restTemplate;

    public TrazabilidadClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void registrarCambioEstado(Long pedidoId, Long clienteId, String estado, String observaciones) {
        String url = "http://localhost:8083/api/trazabilidad/registrar";

        Map<String, Object> body = new HashMap<>();
        body.put("pedidoId", pedidoId);
        body.put("clienteId", clienteId);
        body.put("estado", estado);
        body.put("observaciones", observaciones);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, new HttpHeaders());
        restTemplate.postForEntity(url, request, Void.class);
    }
}

