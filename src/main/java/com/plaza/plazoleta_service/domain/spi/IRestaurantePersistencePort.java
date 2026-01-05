package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRestaurantePersistencePort {

    Restaurante guardarRestaurante(Restaurante restaurante);

    Restaurante obtenerRestaurantePorId(Long id);

    Page<Restaurante> listarRestaurantes(Pageable pageable);
}