package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;
import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.application.handler.CrearRestauranteHandler;
import com.plaza.plazoleta_service.domain.model.Propietario;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IPropietarioClientPort;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import org.springframework.stereotype.Service;

@Service
public class CrearRestauranteHandlerImpl implements CrearRestauranteHandler {

    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPropietarioClientPort propietarioClientPort;

    public CrearRestauranteHandlerImpl(IRestaurantePersistencePort restaurantePersistencePort,
                                       IPropietarioClientPort propietarioClientPort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.propietarioClientPort = propietarioClientPort;
    }

    @Override
    public RestauranteResponse crearRestaurante(CrearRestauranteRequest request) {

        Propietario propietario = propietarioClientPort.obtenerPropietarioPorId(request.getIdPropietario());
        if (propietario == null || !"PROPIETARIO".equals(propietario.getRol())) {
            throw new RuntimeException("El ID del propietario no es válido");
        }

        Restaurante restaurante = new Restaurante(
                request.getNombre(),
                request.getNit(),
                request.getDireccion(),
                request.getTelefono(),
                request.getUrlLogo(),
                request.getIdPropietario()
        );

        Restaurante restauranteGuardado = restaurantePersistencePort.guardarRestaurante(restaurante);

        return new RestauranteResponse(
                restauranteGuardado.getId(),
                restauranteGuardado.getNombre(),
                restauranteGuardado.getNit(),
                restauranteGuardado.getDireccion(),
                restauranteGuardado.getTelefono(),
                restauranteGuardado.getUrlLogo(),
                restauranteGuardado.getIdPropietario()
        );
    }
}