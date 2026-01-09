package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Pedido;
import org.springframework.data.domain.Page;

public interface IListarPedidosService {

    Page<Pedido> listarPedidosPorRestauranteYEstado(
            Long idRestaurante,
            String estado,
            int page,
            int size
    );
}