package com.plaza.plazoleta_service.infrastructure.client;

import com.plaza.plazoleta_service.application.dto.request.MensajeSmsRequest;
import com.plaza.plazoleta_service.application.dto.response.MensajeSmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Component
public class MensajeriaClient {

    private final RestTemplate restTemplate;

    @Value("${mensajeria.url}")
    private String mensajeriaUrl;

    @Value("${mensajeria.apiKey}")
    private String apiKey;

    public MensajeriaClient() {
        this.restTemplate = new RestTemplate();
    }

    public MensajeSmsResponse enviarSms(MensajeSmsRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MensajeSmsRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MensajeSmsResponse> response = restTemplate.exchange(
                mensajeriaUrl + "/mensajeria/enviar",
                HttpMethod.POST,
                entity,
                MensajeSmsResponse.class
        );

        return response.getBody();
    }
}