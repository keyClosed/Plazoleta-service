package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IModificarPlatoService;
import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;

public class ModificarPlatoUseCase implements IModificarPlatoService {

    private final IPlatoPersistencePort platoPersistencePort;

    public ModificarPlatoUseCase(IPlatoPersistencePort platoPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
    }

    @Override
    public Plato modificarPlato(
            Long idPlato,
            Long idPropietario,
            String nuevaDescripcion,
            Long nuevoPrecio
    ) {

        Plato plato = platoPersistencePort.obtenerPlatoPorId(idPlato)
                .orElseThrow(() ->
                        new PlatoException("El plato no existe")
                );

        if (!platoPersistencePort.esPropietarioDelRestaurante(
                idPropietario,
                plato.getIdRestaurante())) {
            throw new PlatoException("Solo el propietario puede modificar el plato");
        }

        if (nuevoPrecio != null) {
            if (nuevoPrecio <= 0) {
                throw new PlatoException("El precio debe ser mayor a 0");
            }
            plato.setPrecio(nuevoPrecio);
        }

        if (nuevaDescripcion != null && !nuevaDescripcion.isBlank()) {
            plato.setDescripcion(nuevaDescripcion);
        }

        return platoPersistencePort.guardarPlato(plato);
    }
}
