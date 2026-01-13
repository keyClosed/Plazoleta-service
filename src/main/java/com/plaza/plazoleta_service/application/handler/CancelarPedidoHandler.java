package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.CancelarPedidoResponse;

public interface CancelarPedidoHandler {
    CancelarPedidoResponse ejecutar(Long pedidoId);
}
