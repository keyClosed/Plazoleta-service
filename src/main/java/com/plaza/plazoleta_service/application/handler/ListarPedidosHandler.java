package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.ListarPedidosResponse;
import org.springframework.data.domain.Page;

public interface ListarPedidosHandler {

    Page<ListarPedidosResponse> listarPedidosPorEstado(Long idRestaurante, String estado, int page, int size);
}