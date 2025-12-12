package com.plaza.plazoleta_service.infrastructure.out.jpa.repository;

import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {

    // Puedes agregar consultas personalizadas si las necesitas
    // Ejemplo: buscar por NIT
    boolean existsByNit(String nit);

    // Ejemplo: buscar por propietario
    // List<RestauranteEntity> findByIdPropietario(String idPropietario);
}
