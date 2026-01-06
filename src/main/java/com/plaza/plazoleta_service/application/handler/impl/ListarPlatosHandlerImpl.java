package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.response.ListarPlatoResponse;
import com.plaza.plazoleta_service.application.handler.ListarPlatosHandler;
import com.plaza.plazoleta_service.application.mapper.ListarPlatoMapper;
import com.plaza.plazoleta_service.application.mapper.PlatoMapper;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.usecase.ListarPlatosUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ListarPlatosHandlerImpl implements ListarPlatosHandler {

    private final ListarPlatosUseCase listarPlatosUseCase;
    private final ListarPlatoMapper listarPlatoMapper;

    public ListarPlatosHandlerImpl(
            ListarPlatosUseCase listarPlatosUseCase,
            ListarPlatoMapper listarPlatoMapper
    ) {
        this.listarPlatosUseCase = listarPlatosUseCase;
        this.listarPlatoMapper = listarPlatoMapper;
    }

    @Override
    public Page<ListarPlatoResponse> listar(
            Long idRestaurante,
            int page,
            int size,
            String categoria
    ) {
        Page<Plato> platos = listarPlatosUseCase.listarPlatos(idRestaurante, page, size, categoria);
        return platos.map(listarPlatoMapper::toListarPlatoResponse);
    }
}
