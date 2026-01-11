package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.AsignarsePedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.AsignarsePedidoResponse;

public interface AsignarsePedidoHandler {
    AsignarsePedidoResponse asignarsePedido(AsignarsePedidoRequest request);
}