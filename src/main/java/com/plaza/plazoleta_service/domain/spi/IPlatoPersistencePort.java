package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Plato;

public interface IPlatoPersistencePort {

    Plato guardarPlato(Plato plato);

    boolean existeRestaurante(Long idRestaurante);

    boolean esPropietarioDelRestaurante(Long idPropietario, Long idRestaurante);
}
