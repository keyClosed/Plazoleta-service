package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Plato;

public interface IModificarPlatoService {

    Plato modificarPlato(
            Long idPlato,
            Long idPropietario,
            String nuevaDescripcion,
            Long nuevoPrecio
    );
}
