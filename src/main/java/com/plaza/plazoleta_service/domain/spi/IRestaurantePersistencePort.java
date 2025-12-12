package com.plaza.plazoleta_service.domain.spi;

import com.plaza.plazoleta_service.domain.model.Restaurante;

import java.util.List;

public interface IRestaurantePersistencePort {

    Restaurante guardarRestaurante(Restaurante restaurante);

    Restaurante obtenerRestaurantePorId(Long id);//se usa despues en otra historia de usuario

    List<Restaurante> listarRestaurantes();//se usa despues en otra historia de usuario

}