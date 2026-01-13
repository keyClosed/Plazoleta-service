package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.ICancelarPedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelarPedidoUseCase implements ICancelarPedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;

    public CancelarPedidoUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
    }

    @Override
    @Transactional
    public Pedido cancelarPedido(Long pedidoId) {

        Pedido pedido = pedidoPersistencePort.obtenerPedidoPorId(pedidoId);

        if (pedido == null) {
            throw new PedidoException("Pedido no encontrado");
        }

        if (!"PENDIENTE".equals(pedido.getEstado())) {
            throw new PedidoException(
                    "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse"
            );
        }

        pedido.setEstado("CANCELADO");

        return pedidoPersistencePort.guardarPedido(pedido);
    }
}
