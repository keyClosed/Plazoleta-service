package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IListarRestaurantesService;
import com.plaza.plazoleta_service.domain.exception.RestauranteException;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ListarRestaurantesUseCase implements IListarRestaurantesService {

    private final IRestaurantePersistencePort restaurantePersistencePort;

    public ListarRestaurantesUseCase(IRestaurantePersistencePort restaurantePersistencePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public Page<Restaurante> listarRestaurantes(int page, int size) {

        if (page < 0) {
            throw new RestauranteException("La página no puede ser negativa");
        }

        if (size <= 0) {
            throw new RestauranteException("El tamaño de página debe ser mayor a 0");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("nombre").ascending()
        );

        return restaurantePersistencePort.listarRestaurantes(pageable);
    }
}