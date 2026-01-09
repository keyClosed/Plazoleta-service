package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.ListarPedidosResponse;
import com.plaza.plazoleta_service.application.dto.response.ListarPlatoPedidoResponse;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListarPedidosMapper {

    private final IPlatoPersistencePort platoPersistencePort;

    public ListarPedidosMapper(IPlatoPersistencePort platoPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
    }

    public ListarPedidosResponse toResponse(Pedido pedido) {

        List<ListarPlatoPedidoResponse> platos = pedido.getPlatos().stream()
                .map(this::toResponsePlatoPedido)
                .collect(Collectors.toList());

        return new ListarPedidosResponse(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getRestauranteId(),
                pedido.getEstado(),
                platos
        );
    }

    private ListarPlatoPedidoResponse toResponsePlatoPedido(PlatoPedido platoPedido) {

        Plato platoBD = platoPersistencePort.obtenerPlatoPorId(platoPedido.getPlatoId())
                .orElseThrow(() -> new RuntimeException(
                        "El plato con ID " + platoPedido.getPlatoId() + " no existe"
                ));

        return new ListarPlatoPedidoResponse(
                platoPedido.getPlatoId(),
                platoPedido.getNombre(),
                platoPedido.getCantidad(),
                platoPedido.getPrecio(),
                platoBD.getCategoria(),
                platoBD.getUrlImagen()
        );

    }
}