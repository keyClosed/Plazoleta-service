package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.CambiarEstadoPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.CambiarEstadoPlatoResponse;
import com.plaza.plazoleta_service.application.handler.CambiarEstadoPlatoHandler;
import com.plaza.plazoleta_service.application.mapper.PlatoMapper;
import com.plaza.plazoleta_service.domain.api.ICambiarEstadoPlatoService;
import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.stereotype.Service;

@Service
public class CambiarEstadoPlatoHandlerImpl implements CambiarEstadoPlatoHandler {

    private final ICambiarEstadoPlatoService cambiarEstadoPlatoService;
    private final PlatoMapper platoMapper;

    public CambiarEstadoPlatoHandlerImpl(
            ICambiarEstadoPlatoService cambiarEstadoPlatoService,
            PlatoMapper platoMapper
    ) {
        this.cambiarEstadoPlatoService = cambiarEstadoPlatoService;
        this.platoMapper = platoMapper;
    }

    @Override
    public CambiarEstadoPlatoResponse cambiarEstadoPlato(
            Long idPlato,
            Long idPropietario,
            CambiarEstadoPlatoRequest request
    ) {

        Plato plato = cambiarEstadoPlatoService.cambiarEstadoPlato(
                idPlato,
                idPropietario,
                request.getActivo()
        );

        return platoMapper.toCambiarEstadoPlatoResponse(plato);
    }
}