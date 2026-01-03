package com.plaza.plazoleta_service.application.mapper;

import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.CambiarEstadoPlatoResponse;
import com.plaza.plazoleta_service.application.dto.response.PlatoResponse;
import com.plaza.plazoleta_service.application.dto.response.ModificarPlatoResponse;
import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.stereotype.Component;

@Component
public class PlatoMapper {

    public Plato toModel(CrearPlatoRequest request) {
        Plato plato = new Plato();
        plato.setNombre(request.getNombre());
        plato.setPrecio(request.getPrecio());
        plato.setDescripcion(request.getDescripcion());
        plato.setUrlImagen(request.getUrlImagen());
        plato.setCategoria(request.getCategoria());
        plato.setIdRestaurante(request.getIdRestaurante());
        return plato;
    }

    public PlatoResponse toResponse(Plato plato) {
        PlatoResponse response = new PlatoResponse();
        response.setId(plato.getId());
        response.setNombre(plato.getNombre());
        response.setPrecio(plato.getPrecio());
        response.setDescripcion(plato.getDescripcion());
        response.setUrlImagen(plato.getUrlImagen());
        response.setCategoria(plato.getCategoria());
        response.setActivo(plato.isActivo());
        response.setIdRestaurante(plato.getIdRestaurante());
        return response;
    }

    public ModificarPlatoResponse toModificarPlatoResponse(Plato plato) {
        if (plato == null) {
            return null;
        }

        return new ModificarPlatoResponse(
                plato.getId(),
                plato.getPrecio(),
                plato.getDescripcion(),
                plato.isActivo()
        );
    }
    public CambiarEstadoPlatoResponse toCambiarEstadoPlatoResponse(Plato plato) {
        if (plato == null) {
            return null;
        }

        return new CambiarEstadoPlatoResponse(
                plato.getId(),
                plato.isActivo()
        );
    }

}
