package com.plaza.plazoleta_service.domain.api;


import com.plaza.plazoleta_service.domain.model.Pedido;

public interface IAsignarsePedidoService {
    Pedido ejecutar(Long idPedido, Long idEmpleado);
}
