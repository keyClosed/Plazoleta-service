package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Pedido;

public interface IRealizarPedido {
    Pedido ejecutar(Pedido pedido) throws Exception;
}
