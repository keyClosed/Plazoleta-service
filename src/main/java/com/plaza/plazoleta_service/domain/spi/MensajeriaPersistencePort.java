package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.application.dto.request.MensajeSmsRequest;
import com.plaza.plazoleta_service.application.dto.response.MensajeSmsResponse;

public interface MensajeriaPersistencePort {

    MensajeSmsResponse enviarSms(MensajeSmsRequest request);
}
