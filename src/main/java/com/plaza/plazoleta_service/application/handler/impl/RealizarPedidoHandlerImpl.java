package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.request.PlatoPedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;
import com.plaza.plazoleta_service.application.dto.response.PlatoPedidoResponse;
import com.plaza.plazoleta_service.application.handler.RealizarPedidoHandler;
import com.plaza.plazoleta_service.domain.api.IRealizarPedido;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.infrastructure.client.UsuariosClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RealizarPedidoHandlerImpl implements RealizarPedidoHandler {

    private final UsuariosClient usuariosClient;
    private final IRealizarPedido realizarPedidoUseCase;

    public RealizarPedidoHandlerImpl(UsuariosClient usuariosClient,
                                     IRealizarPedido realizarPedidoUseCase) {
        this.usuariosClient = usuariosClient;
        this.realizarPedidoUseCase = realizarPedidoUseCase;
    }

    @Override
    public PedidoResponse crearPedido(PedidoRequest request, Long clienteId, String token) throws Exception {

        var cliente = usuariosClient.obtenerClientePorId(clienteId, token);
        if (cliente == null) {
            throw new PedidoException("Cliente no existe");
        }

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(request.getRestauranteId());
        pedido.setClienteTelefono(cliente.getTelefono()); // ← CAMBIO AQUÍ
        pedido.setPinSeguridad(generarPinAleatorio());

        List<PlatoPedido> platos = new ArrayList<>();
        for (PlatoPedidoRequest platoReq : request.getPlatos()) {
            PlatoPedido platoPedido = new PlatoPedido();
            platoPedido.setPlatoId(platoReq.getPlatoId());
            platoPedido.setCantidad(platoReq.getCantidad());
            platos.add(platoPedido);
        }
        pedido.setPlatos(platos);

        Pedido pedidoGuardado = realizarPedidoUseCase.ejecutar(pedido);

        PedidoResponse response = new PedidoResponse();
        response.setId(pedidoGuardado.getId());
        response.setClienteId(pedidoGuardado.getClienteId());
        response.setRestauranteId(pedidoGuardado.getRestauranteId());
        response.setEstado(pedidoGuardado.getEstado());

        List<PlatoPedidoResponse> platosResponse = new ArrayList<>();
        for (PlatoPedido plato : pedidoGuardado.getPlatos()) {
            PlatoPedidoResponse platoResponse = new PlatoPedidoResponse();
            platoResponse.setPlatoId(plato.getPlatoId());
            platoResponse.setNombre(plato.getNombre());
            platoResponse.setCantidad(plato.getCantidad());
            platosResponse.add(platoResponse);
        }
        response.setPlatos(platosResponse);

        return response;
    }

    private Integer generarPinAleatorio() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}