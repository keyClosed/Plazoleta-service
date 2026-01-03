package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.CambiarEstadoPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.CambiarEstadoPlatoResponse;

public interface CambiarEstadoPlatoHandler {

    CambiarEstadoPlatoResponse cambiarEstadoPlato(
            Long idPlato,
            Long idPropietario,
            CambiarEstadoPlatoRequest request
    );
}