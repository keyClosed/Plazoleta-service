package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.CancelarPedidoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class CancelarPedidoMapper {

    public CancelarPedidoResponse toResponse(Pedido pedido, String mensaje) {
        return new CancelarPedidoResponse(
                pedido.getId(),
                pedido.getEstado(),
                mensaje
        );
    }
}
