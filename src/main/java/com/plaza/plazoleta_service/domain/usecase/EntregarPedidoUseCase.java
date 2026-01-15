package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IEntregarPedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregarPedidoUseCase implements IEntregarPedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final ITrazabilidadClientPort trazabilidadClientPort;

    public EntregarPedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                                 ITrazabilidadClientPort trazabilidadClientPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.trazabilidadClientPort = trazabilidadClientPort;
    }

    @Override
    @Transactional
    public Pedido entregarPedido(Long pedidoId, Integer pin) {

        Pedido pedido = pedidoPersistencePort.obtenerPedidoPorId(pedidoId);
        if (pedido == null) {
            throw new PedidoException("Pedido no encontrado");
        }

        if (!"LISTO".equals(pedido.getEstado())) {
            throw new PedidoException("Solo se pueden entregar pedidos en estado LISTO");
        }

        if (pedido.getPinSeguridad() == null) {
            throw new PedidoException("El pedido no tiene PIN de seguridad configurado");
        }

        if (!pedido.getPinSeguridad().equals(pin)) {
            throw new PedidoException("PIN de seguridad incorrecto");
        }

        pedido.setEstado("ENTREGADO");

        Pedido pedidoActualizado = pedidoPersistencePort.guardarPedido(pedido);

        try {
            trazabilidadClientPort.registrarCambioEstado(
                    pedidoActualizado.getId(),
                    pedidoActualizado.getClienteId(),
                    "ENTREGADO",
                    "Pedido entregado al cliente con PIN verificado"
            );
        } catch (Exception e) {
            System.err.println("No se pudo registrar trazabilidad: " + e.getMessage());
        }

        return pedidoActualizado;
    }
}