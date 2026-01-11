package com.plaza.plazoleta_service.infrastructure.out.jpa.repository;

import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {

    @Query("SELECT e.restaurante.id FROM EmpleadoEntity e WHERE e.id = :idEmpleado")
    Long obtenerRestauranteId(@Param("idEmpleado") Long idEmpleado);

}