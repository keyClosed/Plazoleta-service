package com.plaza.plazoleta_service.application.handler.impl;


import com.plaza.plazoleta_service.application.dto.request.AsignarsePedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.AsignarsePedidoResponse;
import com.plaza.plazoleta_service.application.handler.AsignarsePedidoHandler;
import com.plaza.plazoleta_service.application.mapper.AsignarsePedidoMapper;
import com.plaza.plazoleta_service.domain.api.IAsignarsePedidoService;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public class AsignarsePedidoHandlerImpl implements AsignarsePedidoHandler {

    private final IAsignarsePedidoService asignarsePedidoService;
    private final AsignarsePedidoMapper mapper;

    public AsignarsePedidoHandlerImpl(IAsignarsePedidoService asignarsePedidoService,
                                      AsignarsePedidoMapper mapper) {
        this.asignarsePedidoService = asignarsePedidoService;
        this.mapper = mapper;
    }

    @Override
    public AsignarsePedidoResponse asignarsePedido(AsignarsePedidoRequest request) {
        Pedido pedido = asignarsePedidoService.ejecutar(request.getIdPedido(), request.getIdEmpleado());
        return mapper.toResponse(pedido);
    }
}