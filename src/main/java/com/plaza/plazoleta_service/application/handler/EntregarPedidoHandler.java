package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.EntregarPedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.EntregarPedidoResponse;

public interface EntregarPedidoHandler {
    EntregarPedidoResponse ejecutar(Long pedidoId, EntregarPedidoRequest request);
}