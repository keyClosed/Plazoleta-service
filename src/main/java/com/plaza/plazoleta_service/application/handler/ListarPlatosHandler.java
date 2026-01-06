package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.ListarPlatoResponse;
import org.springframework.data.domain.Page;

public interface ListarPlatosHandler {

    Page<ListarPlatoResponse> listar(
            Long idRestaurante,
            int page,
            int size,
            String categoria
    );
}
