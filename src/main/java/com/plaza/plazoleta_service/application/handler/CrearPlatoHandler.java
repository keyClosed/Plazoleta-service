package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.PlatoResponse;

public interface CrearPlatoHandler {

    PlatoResponse crearPlato(CrearPlatoRequest request, Long idPropietario);

}
