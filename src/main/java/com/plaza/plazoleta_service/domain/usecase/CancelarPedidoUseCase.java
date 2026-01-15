package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.ICancelarPedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelarPedidoUseCase implements ICancelarPedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final ITrazabilidadClientPort trazabilidadClientPort;

    public CancelarPedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                                 ITrazabilidadClientPort trazabilidadClientPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.trazabilidadClientPort = trazabilidadClientPort;
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

        Pedido pedidoActualizado = pedidoPersistencePort.guardarPedido(pedido);

        try {
            trazabilidadClientPort.registrarCambioEstado(
                    pedidoActualizado.getId(),
                    pedidoActualizado.getClienteId(),
                    "CANCELADO",
                    "Pedido cancelado por el cliente"
            );
        } catch (Exception e) {
            System.err.println("No se pudo registrar trazabilidad: " + e.getMessage());
        }

        return pedidoActualizado;
    }
}