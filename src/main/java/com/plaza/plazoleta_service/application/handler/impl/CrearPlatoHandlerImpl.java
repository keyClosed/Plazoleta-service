package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.PlatoResponse;
import com.plaza.plazoleta_service.application.handler.CrearPlatoHandler;
import com.plaza.plazoleta_service.application.mapper.PlatoMapper;
import com.plaza.plazoleta_service.domain.api.ICrearPlatoService;
import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.stereotype.Service;

@Service
public class CrearPlatoHandlerImpl implements CrearPlatoHandler {

    private final ICrearPlatoService crearPlatoService;
    private final PlatoMapper platoMapper;

    public CrearPlatoHandlerImpl(ICrearPlatoService crearPlatoService, PlatoMapper platoMapper) {
        this.crearPlatoService = crearPlatoService;
        this.platoMapper = platoMapper;
    }

    @Override
    public PlatoResponse crearPlato(CrearPlatoRequest request, Long idPropietario) {
        Plato plato = platoMapper.toModel(request);
        Plato platoCreado = crearPlatoService.crearPlato(plato, idPropietario);
        return platoMapper.toResponse(platoCreado);
    }
}
