package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.ModificarPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.ModificarPlatoResponse;

public interface ModificarPlatoHandler {

    ModificarPlatoResponse modificarPlato(
            Long idPlato,
            Long idPropietario,
            ModificarPlatoRequest request
    );
}
