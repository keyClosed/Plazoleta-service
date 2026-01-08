package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IRealizarPedido;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import org.springframework.stereotype.Service;

@Service
public class RealizarPedidoUseCase implements IRealizarPedido {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;

    public RealizarPedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                                 IPlatoPersistencePort platoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.platoPersistencePort = platoPersistencePort;
    }

    @Override
    public Pedido ejecutar(Pedido pedido) {

        if (pedidoPersistencePort.tienePedidoEnProceso(pedido.getClienteId())) {
            throw new PedidoException("El cliente ya tiene un pedido en proceso");
        }

        if (pedido.getPlatos() == null || pedido.getPlatos().isEmpty()) {
            throw new PedidoException("No se puede crear un pedido sin platos");
        }

        for (PlatoPedido platoPedido : pedido.getPlatos()) {

            if (platoPedido.getCantidad() <= 0) {
                throw new PedidoException("La cantidad de cada plato debe ser mayor a 0");
            }

            Plato platoBD = platoPersistencePort.obtenerPlatoPorId(platoPedido.getPlatoId())
                    .orElseThrow(() -> new PedidoException(
                            "El plato con ID " + platoPedido.getPlatoId() + " no existe"
                    ));


            if (!platoBD.getIdRestaurante().equals(pedido.getRestauranteId())) {
                throw new PedidoException("Todos los platos deben pertenecer al mismo restaurante");
            }

            platoPedido.setNombre(platoBD.getNombre());
            platoPedido.setPrecio(platoBD.getPrecio());
        }

        pedido.setEstado("PENDIENTE");

        return pedidoPersistencePort.guardarPedido(pedido);
    }
}