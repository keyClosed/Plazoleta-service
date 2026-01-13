package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.EntregarPedidoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class EntregarPedidoMapper {

    public EntregarPedidoResponse toResponse(Pedido pedido, String mensaje) {
        EntregarPedidoResponse response = new EntregarPedidoResponse();
        response.setId(pedido.getId());
        response.setEstado(pedido.getEstado());
        response.setMensaje(mensaje);
        return response;
    }
}