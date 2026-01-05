package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.ListarRestauranteResponse;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class ListarRestauranteMapper {

    public ListarRestauranteResponse toResponse(Restaurante restaurante) {
        return new ListarRestauranteResponse(
                restaurante.getNombre(),
                restaurante.getUrlLogo()
        );
    }
}