package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.response.CancelarPedidoResponse;
import com.plaza.plazoleta_service.application.handler.CancelarPedidoHandler;
import com.plaza.plazoleta_service.application.mapper.CancelarPedidoMapper;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.usecase.CancelarPedidoUseCase;
import org.springframework.stereotype.Service;

@Service
public class CancelarPedidoHandlerImpl implements CancelarPedidoHandler {

    private final CancelarPedidoUseCase cancelarPedidoUseCase;
    private final CancelarPedidoMapper cancelarPedidoMapper;

    public CancelarPedidoHandlerImpl(
            CancelarPedidoUseCase cancelarPedidoUseCase,
            CancelarPedidoMapper cancelarPedidoMapper) {
        this.cancelarPedidoUseCase = cancelarPedidoUseCase;
        this.cancelarPedidoMapper = cancelarPedidoMapper;
    }

    @Override
    public CancelarPedidoResponse ejecutar(Long pedidoId) {
        Pedido pedido = cancelarPedidoUseCase.cancelarPedido(pedidoId);
        return cancelarPedidoMapper.toResponse(pedido, "Pedido cancelado correctamente");
    }
}
