package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.response.ListarRestauranteResponse;
import com.plaza.plazoleta_service.application.handler.ListarRestaurantesHandler;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.usecase.ListarRestaurantesUseCase;
import com.plaza.plazoleta_service.application.mapper.ListarRestauranteMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
public class ListarRestaurantesHandlerImpl implements ListarRestaurantesHandler {

    private final ListarRestaurantesUseCase listarRestaurantesUseCase;
    private final ListarRestauranteMapper listarRestauranteMapper;

    public ListarRestaurantesHandlerImpl(
            ListarRestaurantesUseCase listarRestaurantesUseCase,
            ListarRestauranteMapper listarRestauranteMapper
    ) {
        this.listarRestaurantesUseCase = listarRestaurantesUseCase;
        this.listarRestauranteMapper = listarRestauranteMapper;
    }

    @Override
    public Page<ListarRestauranteResponse> listar(int page, int size) {
        return listarRestaurantesUseCase
                .listarRestaurantes(page, size)
                .map(listarRestauranteMapper::toResponse);
    }
}