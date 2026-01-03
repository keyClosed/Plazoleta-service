package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.ModificarPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.ModificarPlatoResponse;
import com.plaza.plazoleta_service.application.handler.ModificarPlatoHandler;
import com.plaza.plazoleta_service.application.mapper.PlatoMapper;
import com.plaza.plazoleta_service.domain.api.IModificarPlatoService;
import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.stereotype.Service;

@Service
public class ModificarPlatoHandlerImpl implements ModificarPlatoHandler {

    private final IModificarPlatoService modificarPlatoService;
    private final PlatoMapper platoMapper;

    public ModificarPlatoHandlerImpl(
            IModificarPlatoService modificarPlatoService,
            PlatoMapper platoMapper
    ) {
        this.modificarPlatoService = modificarPlatoService;
        this.platoMapper = platoMapper;
    }

    @Override
    public ModificarPlatoResponse modificarPlato(
            Long idPlato,
            Long idPropietario,
            ModificarPlatoRequest request
    ) {

        Plato platoModificado = modificarPlatoService.modificarPlato(
                idPlato,
                idPropietario,
                request.getDescripcion(),
                request.getPrecio()
        );

        return platoMapper.toModificarPlatoResponse(platoModificado);
    }
}
