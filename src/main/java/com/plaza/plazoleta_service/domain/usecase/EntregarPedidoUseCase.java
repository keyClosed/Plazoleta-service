package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IEntregarPedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregarPedidoUseCase implements IEntregarPedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;

    public EntregarPedidoUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
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

        return pedidoPersistencePort.guardarPedido(pedido);
    }
}