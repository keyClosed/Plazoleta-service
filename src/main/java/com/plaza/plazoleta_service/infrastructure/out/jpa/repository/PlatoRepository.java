package com.plaza.plazoleta_service.infrastructure.out.jpa.repository;

import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {

    Page<PlatoEntity> findByIdRestaurante(Long idRestaurante, Pageable pageable);

    // Listar platos por restaurante y categoría paginados
    Page<PlatoEntity> findByIdRestauranteAndCategoriaIgnoreCase(
            Long idRestaurante,
            String categoria,
            Pageable pageable
    );
}