package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.response.PedidoListoResponse;
import com.plaza.plazoleta_service.application.handler.PedidoListoHandler;
import com.plaza.plazoleta_service.application.mapper.PedidoListoMapper;
import com.plaza.plazoleta_service.domain.usecase.PedidoListoUseCase;
import org.springframework.stereotype.Service;

@Service
public class PedidoListoHandlerImpl implements PedidoListoHandler {

    private final PedidoListoUseCase pedidoListoUseCase;
    private final PedidoListoMapper pedidoListoMapper;

    public PedidoListoHandlerImpl(PedidoListoUseCase pedidoListoUseCase,
                                  PedidoListoMapper pedidoListoMapper) {
        this.pedidoListoUseCase = pedidoListoUseCase;
        this.pedidoListoMapper = pedidoListoMapper;
    }

    @Override
    public PedidoListoResponse ejecutar(Long idPedido) {
        var pedido = pedidoListoUseCase.marcarPedidoComoListo(idPedido);
        String mensaje = "Mensaje SMS enviado al cliente";
        return pedidoListoMapper.toResponse(pedido, mensaje);
    }
}