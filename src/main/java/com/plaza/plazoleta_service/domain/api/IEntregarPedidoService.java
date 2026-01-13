package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Pedido;

public interface IEntregarPedidoService {
    Pedido entregarPedido(Long pedidoId, Integer pin);
}