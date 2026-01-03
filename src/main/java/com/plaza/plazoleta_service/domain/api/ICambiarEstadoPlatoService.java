package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Plato;

public interface ICambiarEstadoPlatoService {

    Plato cambiarEstadoPlato(
            Long idPlato,
            Long idPropietario,
            boolean activo
    );
}