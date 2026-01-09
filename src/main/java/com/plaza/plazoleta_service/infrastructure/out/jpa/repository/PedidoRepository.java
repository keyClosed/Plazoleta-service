package com.plaza.plazoleta_service.infrastructure.out.jpa.repository;

import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByClienteIdAndEstadoIn(Long clienteId, List<String> estados);

    List<PedidoEntity> findByClienteId(Long clienteId);
    Page<PedidoEntity> findByRestauranteIdAndEstado(Long restauranteId, String estado, Pageable pageable);
}
