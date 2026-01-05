package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.ListarRestauranteResponse;
import org.springframework.data.domain.Page;

public interface ListarRestaurantesHandler {
    Page<ListarRestauranteResponse> listar(int page, int size);
}
