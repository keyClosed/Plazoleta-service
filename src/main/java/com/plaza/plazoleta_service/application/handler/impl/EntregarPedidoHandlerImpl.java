package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.EntregarPedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.EntregarPedidoResponse;
import com.plaza.plazoleta_service.application.handler.EntregarPedidoHandler;
import com.plaza.plazoleta_service.application.mapper.EntregarPedidoMapper;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.usecase.EntregarPedidoUseCase;
import org.springframework.stereotype.Service;

@Service
public class EntregarPedidoHandlerImpl implements EntregarPedidoHandler {

    private final EntregarPedidoUseCase entregarPedidoUseCase;
    private final EntregarPedidoMapper entregarPedidoMapper;

    public EntregarPedidoHandlerImpl(EntregarPedidoUseCase entregarPedidoUseCase,
                                     EntregarPedidoMapper entregarPedidoMapper) {
        this.entregarPedidoUseCase = entregarPedidoUseCase;
        this.entregarPedidoMapper = entregarPedidoMapper;
    }

    @Override
    public EntregarPedidoResponse ejecutar(Long pedidoId, EntregarPedidoRequest request) {
        Pedido pedido = entregarPedidoUseCase.entregarPedido(pedidoId, request.getPin());
        return entregarPedidoMapper.toResponse(pedido, "Pedido entregado correctamente");
    }
}