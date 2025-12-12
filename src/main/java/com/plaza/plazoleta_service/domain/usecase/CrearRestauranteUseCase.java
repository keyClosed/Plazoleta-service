package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.ICrearRestauranteService;
import com.plaza.plazoleta_service.domain.exception.RestauranteException;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;

public class CrearRestauranteUseCase implements ICrearRestauranteService {

    private final IRestaurantePersistencePort restaurantePersistencePort;

    public CrearRestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public Restaurante crearRestaurante(Restaurante restaurante) {

        if (restaurante.getNombre().matches("^\\d+$")) {
            throw new RestauranteException("El nombre del restaurante no puede ser solo números");
        }

        if (!restaurante.getNit().matches("^[0-9]+$")) {
            throw new RestauranteException("El NIT debe ser numérico");
        }

        if (!restaurante.getTelefono().matches("^\\+?[0-9]{1,13}$")) {
            throw new RestauranteException("Teléfono inválido. Máximo 13 caracteres, puede iniciar con +");
        }

        // validar que idPropietario corresponda a un usuario con rol propietario con el microservicio usuarios

        return restaurantePersistencePort.guardarRestaurante(restaurante);
    }
}