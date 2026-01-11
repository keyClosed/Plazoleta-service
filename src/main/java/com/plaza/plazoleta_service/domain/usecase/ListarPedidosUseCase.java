package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IListarPedidosService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ListarPedidosUseCase implements IListarPedidosService {

    private final IPedidoPersistencePort pedidoPersistencePort;

    public ListarPedidosUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
    }

    @Override
    public Page<Pedido> listarPedidosPorRestauranteYEstado(Long idRestaurante, String estado, int page, int size) {

        if (page < 0) {
            throw new PedidoException("La página no puede ser negativa");
        }

        if (size <= 0) {
            throw new PedidoException("El tamaño de página debe ser mayor a 0");
        }

        Pageable pageable = PageRequest.of(page, size);

        return pedidoPersistencePort.listarPedidosPorRestauranteYEstado(idRestaurante, estado, pageable);
    }
}