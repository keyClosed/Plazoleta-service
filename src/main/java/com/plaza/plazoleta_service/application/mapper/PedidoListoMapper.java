package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.PedidoListoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoListoMapper {

    public PedidoListoResponse toResponse(Pedido pedido, String mensaje) {
        return new PedidoListoResponse(
                pedido.getId(),
                pedido.getEstado(),
                pedido.getClienteTelefono(),
                pedido.getPinSeguridad(),
                mensaje
        );
    }
}