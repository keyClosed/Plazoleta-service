package com.plaza.plazoleta_service.infrastructure.out.jpa.repository;

import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {
}