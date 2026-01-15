package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IAsignarsePedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignarsePedidoUseCase implements IAsignarsePedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final ITrazabilidadClientPort trazabilidadClientPort;

    public AsignarsePedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                                  ITrazabilidadClientPort trazabilidadClientPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.trazabilidadClientPort = trazabilidadClientPort;
    }

    @Override
    @Transactional
    public Pedido ejecutar(Long idPedido, Long idEmpleado) {

        Pedido pedido = pedidoPersistencePort.obtenerPedidoPorId(idPedido);

        if (pedido == null) {
            throw new PedidoException("Pedido no encontrado");
        }

        pedido.setEmpleadoAsignadoId(idEmpleado);
        pedido.setEstado("EN_PREPARACION");

        Pedido pedidoActualizado = pedidoPersistencePort.guardarPedido(pedido);

        try {
            trazabilidadClientPort.registrarCambioEstado(
                    pedidoActualizado.getId(),
                    pedidoActualizado.getClienteId(),
                    "EN_PREPARACION",
                    "Empleado " + idEmpleado + " asignado al pedido"
            );
        } catch (Exception e) {
            System.err.println("No se pudo registrar trazabilidad: " + e.getMessage());
        }

        return pedidoActualizado;
    }
}