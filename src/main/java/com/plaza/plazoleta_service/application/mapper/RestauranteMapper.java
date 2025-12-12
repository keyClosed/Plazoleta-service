package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;
import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteMapper {

    public Restaurante requestToRestaurante(CrearRestauranteRequest request) {
        return new Restaurante(
                request.getNombre(),
                request.getNit(),
                request.getDireccion(),
                request.getTelefono(),
                request.getUrlLogo(),
                request.getIdPropietario()
        );
    }

    public RestauranteResponse restauranteToResponse(Restaurante restaurante) {
        return new RestauranteResponse(
                restaurante.getId(),
                restaurante.getNombre(),
                restaurante.getNit(),
                restaurante.getDireccion(),
                restaurante.getTelefono(),
                restaurante.getUrlLogo(),
                restaurante.getIdPropietario()
        );
    }
}
