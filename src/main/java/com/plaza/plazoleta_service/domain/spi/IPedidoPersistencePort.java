package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Pedido;
import java.util.List;

public interface IPedidoPersistencePort {

    Pedido guardarPedido(Pedido pedido);

    boolean tienePedidoEnProceso(Long clienteId);

    Pedido obtenerPedidoPorId(Long id);
    List<Pedido> listarPedidosPorCliente(Long clienteId);

}
