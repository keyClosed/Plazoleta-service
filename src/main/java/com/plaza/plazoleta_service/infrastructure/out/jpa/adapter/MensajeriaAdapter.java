package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.application.dto.request.MensajeSmsRequest;
import com.plaza.plazoleta_service.application.dto.response.MensajeSmsResponse;
import com.plaza.plazoleta_service.domain.spi.MensajeriaPersistencePort;
import com.plaza.plazoleta_service.infrastructure.client.MensajeriaClient;
import org.springframework.stereotype.Component;

@Component
public class MensajeriaAdapter implements MensajeriaPersistencePort {

    private final MensajeriaClient mensajeriaClient;

    public MensajeriaAdapter(MensajeriaClient mensajeriaClient) {
        this.mensajeriaClient = mensajeriaClient;
    }

    @Override
    public MensajeSmsResponse enviarSms(MensajeSmsRequest request) {

        return mensajeriaClient.enviarSms(request);
    }
}