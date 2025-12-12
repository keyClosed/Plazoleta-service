package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Propietario;

public interface IPropietarioClientPort {
    Propietario obtenerPropietarioPorId(long id);
}
