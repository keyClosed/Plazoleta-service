package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.ICrearPlatoService;
import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;

public class CrearPlatoUseCase implements ICrearPlatoService {

    private final IPlatoPersistencePort platoPersistencePort;

    public CrearPlatoUseCase(IPlatoPersistencePort platoPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
    }

    @Override
    public Plato crearPlato(Plato plato, Long idPropietario) {

        validarCampos(plato);
        validarRestaurante(plato);
        validarPropietario(idPropietario, plato.getIdRestaurante());
        plato.setActivo(true);
        return platoPersistencePort.guardarPlato(plato);
    }

    private void validarCampos(Plato plato) {
        if (plato.getNombre() == null || plato.getNombre().isBlank()) {
            throw new PlatoException("El nombre del plato es obligatorio");
        }
        if (plato.getDescripcion() == null || plato.getDescripcion().isBlank()) {
            throw new PlatoException("La descripción del plato es obligatoria");
        }
        if (plato.getUrlImagen() == null || plato.getUrlImagen().isBlank()) {
            throw new PlatoException("La URL de la imagen es obligatoria");
        }
        if (plato.getCategoria() == null || plato.getCategoria().isBlank()) {
            throw new PlatoException("La categoría es obligatoria");
        }
        if (plato.getPrecio() == null || plato.getPrecio() <= 0) {
            throw new PlatoException("El precio debe ser mayor a 0");
        }
    }

    private void validarRestaurante(Plato plato) {
        if (!platoPersistencePort.existeRestaurante(plato.getIdRestaurante())) {
            throw new PlatoException("El restaurante no existe");
        }
    }

    private void validarPropietario(Long idPropietario, Long idRestaurante) {
        if (!platoPersistencePort.esPropietarioDelRestaurante(idPropietario, idRestaurante)) {
            throw new PlatoException("Solo el propietario del restaurante puede crear platos");
        }
    }
}
