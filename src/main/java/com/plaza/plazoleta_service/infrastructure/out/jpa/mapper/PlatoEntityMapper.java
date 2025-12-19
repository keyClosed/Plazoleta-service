package com.plaza.plazoleta_service.infrastructure.out.jpa.mapper;

import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PlatoEntity;
import org.springframework.stereotype.Component;

@Component
public class PlatoEntityMapper {

    public PlatoEntity toEntity(Plato model) {
        PlatoEntity entity = new PlatoEntity();
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPrecio(model.getPrecio());
        entity.setUrlImagen(model.getUrlImagen());
        entity.setCategoria(model.getCategoria());
        entity.setIdRestaurante(model.getIdRestaurante());
        entity.setIdPropietario(model.getIdPropietario());
        entity.setActivo(model.isActivo());
        return entity;
    }

    public Plato toModel(PlatoEntity entity) {
        Plato model = new Plato();
        model.setId(entity.getId());
        model.setNombre(entity.getNombre());
        model.setDescripcion(entity.getDescripcion());
        model.setPrecio(entity.getPrecio());
        model.setUrlImagen(entity.getUrlImagen());
        model.setCategoria(entity.getCategoria());
        model.setIdRestaurante(entity.getIdRestaurante());
        model.setIdPropietario(entity.getIdPropietario());
        model.setActivo(entity.isActivo());
        return model;
    }
}
