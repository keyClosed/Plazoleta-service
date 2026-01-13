package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.response.PedidoListoResponse;

public interface PedidoListoHandler {
    PedidoListoResponse ejecutar(Long idPedido);
}
