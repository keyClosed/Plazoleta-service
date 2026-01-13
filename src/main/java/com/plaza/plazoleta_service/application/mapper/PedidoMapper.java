package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.request.PlatoPedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;
import com.plaza.plazoleta_service.application.dto.response.PlatoPedidoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toModel(PedidoRequest request, Long clienteId) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(request.getRestauranteId());

        List<PlatoPedido> platos = request.getPlatos().stream()
                .map(this::toModelPlatoPedido)
                .collect(Collectors.toList());
        pedido.setPlatos(platos);

        return pedido;
    }
    private PlatoPedido toModelPlatoPedido(PlatoPedidoRequest request) {
        PlatoPedido platoPedido = new PlatoPedido();
        platoPedido.setPlatoId(request.getPlatoId());
        platoPedido.setCantidad(request.getCantidad());
        return platoPedido;
    }

    public PedidoResponse toResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setClienteId(pedido.getClienteId());
        response.setRestauranteId(pedido.getRestauranteId());
        response.setEstado(pedido.getEstado());

        List<PlatoPedidoResponse> platos = pedido.getPlatos().stream()
                .map(this::toResponsePlatoPedido)
                .collect(Collectors.toList());
        response.setPlatos(platos);

        return response;
    }

    private PlatoPedidoResponse toResponsePlatoPedido(PlatoPedido platoPedido) {
        PlatoPedidoResponse response = new PlatoPedidoResponse();
        response.setPlatoId(platoPedido.getPlatoId());
        response.setCantidad(platoPedido.getCantidad());
        response.setNombre(platoPedido.getNombre());
        return response;
    }
}