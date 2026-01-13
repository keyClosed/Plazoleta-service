package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Pedido;

public interface ICancelarPedidoService {
    Pedido cancelarPedido(Long pedidoId);
}
