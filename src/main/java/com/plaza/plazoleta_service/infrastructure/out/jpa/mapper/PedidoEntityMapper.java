package com.plaza.plazoleta_service.infrastructure.out.jpa.mapper;

import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PedidoEntity;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PlatoPedidoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoEntityMapper {

    public Pedido entityToPedido(PedidoEntity entity) {
        if (entity == null) return null;

        Pedido pedido = new Pedido();
        pedido.setId(entity.getId());
        pedido.setClienteId(entity.getClienteId());
        pedido.setRestauranteId(entity.getRestauranteId());
        pedido.setEstado(entity.getEstado());
        pedido.setEmpleadoAsignadoId(entity.getEmpleadoAsignado());
        pedido.setClienteTelefono(entity.getTelefonoCliente());
        pedido.setPinSeguridad(entity.getPinSeguridad());

        List<PlatoPedido> platos = entity.getPlatos().stream().map(platoEntity -> {
            PlatoPedido plato = new PlatoPedido();
            plato.setPlatoId(platoEntity.getPlatoId());
            plato.setNombre(platoEntity.getNombre());
            plato.setCantidad(platoEntity.getCantidad());
            plato.setPrecio(platoEntity.getPrecio());
            return plato;
        }).collect(Collectors.toList());

        pedido.setPlatos(platos);

        return pedido;
    }

    public PedidoEntity pedidoToEntity(Pedido pedido) {
        if (pedido == null) return null;

        PedidoEntity entity = new PedidoEntity();
        entity.setId(pedido.getId());
        entity.setClienteId(pedido.getClienteId());
        entity.setRestauranteId(pedido.getRestauranteId());
        entity.setEstado(pedido.getEstado());
        entity.setEmpleadoAsignado(pedido.getEmpleadoAsignadoId());
        entity.setTelefonoCliente(pedido.getClienteTelefono());
        entity.setPinSeguridad(pedido.getPinSeguridad());


        List<PlatoPedidoEntity> platosEntity = pedido.getPlatos().stream().map(plato -> {
            PlatoPedidoEntity platoEntity = new PlatoPedidoEntity();
            platoEntity.setPlatoId(plato.getPlatoId());
            platoEntity.setNombre(plato.getNombre());
            platoEntity.setCantidad(plato.getCantidad());
            platoEntity.setPrecio(plato.getPrecio());
            platoEntity.setPedido(entity);
            return platoEntity;
        }).collect(Collectors.toList());

        entity.setPlatos(platosEntity);

        return entity;
    }
}