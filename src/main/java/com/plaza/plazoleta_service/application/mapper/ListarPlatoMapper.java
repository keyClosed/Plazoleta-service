package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.response.ListarPlatoResponse;
import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.stereotype.Component;

@Component
public class ListarPlatoMapper {

    public ListarPlatoResponse toListarPlatoResponse(Plato plato) {
        return new ListarPlatoResponse(
                plato.getNombre(),
                plato.getPrecio(),
                plato.getCategoria(),
                plato.getUrlImagen()
        );
    }
}