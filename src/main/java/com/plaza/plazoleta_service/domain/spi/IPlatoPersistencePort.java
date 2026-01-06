package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPlatoPersistencePort {

    Plato guardarPlato(Plato plato);

    boolean existeRestaurante(Long idRestaurante);

    boolean esPropietarioDelRestaurante(Long idPropietario, Long idRestaurante);
    Optional<Plato> obtenerPlatoPorId(Long idPlato);
    Page<Plato> listarPlatos(Long idRestaurante, String categoria, Pageable pageable);
}
