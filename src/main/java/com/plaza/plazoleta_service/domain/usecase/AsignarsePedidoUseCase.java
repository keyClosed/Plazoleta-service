package com.plaza.plazoleta_service.domain.usecase;


import com.plaza.plazoleta_service.domain.api.IAsignarsePedidoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignarsePedidoUseCase implements IAsignarsePedidoService {

    private final IPedidoPersistencePort pedidoPersistencePort;

    public AsignarsePedidoUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
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

        return pedidoPersistencePort.guardarPedido(pedido);
    }
}