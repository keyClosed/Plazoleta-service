package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.AsignarsePedidoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class AsignarsePedidoMapper {

    public AsignarsePedidoResponse toResponse(Pedido pedido) {
        return new AsignarsePedidoResponse(
                pedido.getId(),
                pedido.getEmpleadoAsignadoId(),
                pedido.getEstado()
        );
    }
}