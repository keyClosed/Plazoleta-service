package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.ICambiarEstadoPlatoService;
import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;

public class CambiarEstadoPlatoUseCase implements ICambiarEstadoPlatoService {

    private final IPlatoPersistencePort platoPersistencePort;

    public CambiarEstadoPlatoUseCase(IPlatoPersistencePort platoPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
    }

    @Override
    public Plato cambiarEstadoPlato(
            Long idPlato,
            Long idPropietario,
            boolean activo
    ) {

        Plato plato = platoPersistencePort.obtenerPlatoPorId(idPlato)
                .orElseThrow(() ->
                        new PlatoException("El plato no existe")
                );

        if (!platoPersistencePort.esPropietarioDelRestaurante(
                idPropietario,
                plato.getIdRestaurante())) {
            throw new PlatoException("Solo el propietario puede habilitar o deshabilitar el plato");
        }

        plato.setActivo(activo);

        return platoPersistencePort.guardarPlato(plato);
    }
}
