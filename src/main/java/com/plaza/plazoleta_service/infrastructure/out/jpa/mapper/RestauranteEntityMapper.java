package com.plaza.plazoleta_service.infrastructure.out.jpa.mapper;

import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.RestauranteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestauranteEntityMapper {

    // Modelo de dominio → Entidad JPA
    public RestauranteEntity restauranteToEntity(Restaurante restaurante) {
        if (restaurante == null) return null;

        RestauranteEntity entity = new RestauranteEntity();
        entity.setId(restaurante.getId());
        entity.setNombre(restaurante.getNombre());
        entity.setNit(restaurante.getNit());
        entity.setDireccion(restaurante.getDireccion());
        entity.setTelefono(restaurante.getTelefono());
        entity.setUrlLogo(restaurante.getUrlLogo());
        entity.setIdPropietario(restaurante.getIdPropietario());
        return entity;
    }

    // Entidad JPA → Modelo de dominio
    public Restaurante entityToRestaurante(RestauranteEntity entity) {
        if (entity == null) return null;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(entity.getId());
        restaurante.setNombre(entity.getNombre());
        restaurante.setNit(entity.getNit());
        restaurante.setDireccion(entity.getDireccion());
        restaurante.setTelefono(entity.getTelefono());
        restaurante.setUrlLogo(entity.getUrlLogo());
        restaurante.setIdPropietario(entity.getIdPropietario());
        return restaurante;
    }
}
