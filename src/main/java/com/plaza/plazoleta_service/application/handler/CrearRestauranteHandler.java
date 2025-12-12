package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;

public interface CrearRestauranteHandler {
    RestauranteResponse crearRestaurante(CrearRestauranteRequest request);
}
