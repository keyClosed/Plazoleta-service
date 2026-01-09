package com.plaza.plazoleta_service.application.handler.impl;


import com.plaza.plazoleta_service.application.dto.response.ListarPedidosResponse;
import com.plaza.plazoleta_service.application.handler.ListarPedidosHandler;
import com.plaza.plazoleta_service.application.mapper.ListarPedidosMapper;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.usecase.ListarPedidosUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListarPedidosHandlerImpl implements ListarPedidosHandler {

    private final ListarPedidosUseCase listarPedidosUseCase;
    private final ListarPedidosMapper listarPedidosMapper;

    public ListarPedidosHandlerImpl(
            ListarPedidosUseCase listarPedidosUseCase,
            ListarPedidosMapper listarPedidosMapper
    ) {
        this.listarPedidosUseCase = listarPedidosUseCase;
        this.listarPedidosMapper = listarPedidosMapper;
    }

    @Override
    public Page<ListarPedidosResponse> listarPedidosPorEstado(Long idRestaurante, String estado, int page, int size) {
        Page<Pedido> pedidos = listarPedidosUseCase.listarPedidosPorRestauranteYEstado(idRestaurante, estado, page, size);
        return pedidos.map(listarPedidosMapper::toResponse);
    }
}