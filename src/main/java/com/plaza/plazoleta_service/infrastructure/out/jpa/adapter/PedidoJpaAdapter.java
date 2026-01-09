package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PedidoEntity;
import com.plaza.plazoleta_service.infrastructure.out.jpa.mapper.PedidoEntityMapper;
import com.plaza.plazoleta_service.infrastructure.out.jpa.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final PedidoRepository pedidoRepository;
    private final PedidoEntityMapper pedidoEntityMapper;

    public PedidoJpaAdapter(PedidoRepository pedidoRepository,
                            PedidoEntityMapper pedidoEntityMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoEntityMapper = pedidoEntityMapper;
    }

    @Override
    public boolean tienePedidoEnProceso(Long clienteId) {
        List<String> estados = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return !pedidoRepository.findByClienteIdAndEstadoIn(clienteId, estados).isEmpty();
    }

    @Override
    @Transactional
    public Pedido guardarPedido(Pedido pedido) {

        PedidoEntity pedidoEntity = pedidoEntityMapper.pedidoToEntity(pedido);

        PedidoEntity guardado = pedidoRepository.save(pedidoEntity);

        return pedidoEntityMapper.entityToPedido(guardado);
    }

    @Override
    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoEntityMapper::entityToPedido)
                .orElse(null);
    }

    @Override
    public List<Pedido> listarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(pedidoEntityMapper::entityToPedido)
                .collect(Collectors.toList());
    }
    @Override
    public Page<Pedido> listarPedidosPorRestauranteYEstado(Long idRestaurante, String estado, Pageable pageable) {
        return pedidoRepository.findByRestauranteIdAndEstado(idRestaurante, estado, pageable)
                .map(pedidoEntityMapper::entityToPedido);
    }

}