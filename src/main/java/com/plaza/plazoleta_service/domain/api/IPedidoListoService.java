package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Pedido;

public interface IPedidoListoService {

    Pedido marcarPedidoComoListo(Long idPedido);

}
