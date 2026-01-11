package com.plaza.plazoleta_service.application.handler;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;

public interface RealizarPedidoHandler {

    PedidoResponse crearPedido(PedidoRequest request) throws Exception;
}