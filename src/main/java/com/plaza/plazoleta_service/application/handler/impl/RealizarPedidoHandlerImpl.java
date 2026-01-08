package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;
import com.plaza.plazoleta_service.application.handler.RealizarPedidoHandler;
import com.plaza.plazoleta_service.application.mapper.PedidoMapper;
import com.plaza.plazoleta_service.domain.api.IRealizarPedido;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public class RealizarPedidoHandlerImpl implements RealizarPedidoHandler {

    private final IRealizarPedido realizarPedido;
    private final PedidoMapper pedidoMapper;

    public RealizarPedidoHandlerImpl(IRealizarPedido realizarPedido, PedidoMapper pedidoMapper) {
        this.realizarPedido = realizarPedido;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoResponse crearPedido(PedidoRequest request) throws Exception {

        Pedido pedido = pedidoMapper.toModel(request);

        Pedido pedidoGuardado = realizarPedido.ejecutar(pedido);

        return pedidoMapper.toResponse(pedidoGuardado);
    }
}