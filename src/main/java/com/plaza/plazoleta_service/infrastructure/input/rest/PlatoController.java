package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.PlatoResponse;
import com.plaza.plazoleta_service.application.handler.CrearPlatoHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final CrearPlatoHandler crearPlatoHandler;

    public PlatoController(CrearPlatoHandler crearPlatoHandler) {
        this.crearPlatoHandler = crearPlatoHandler;
    }

    @PostMapping
    public ResponseEntity<PlatoResponse> crearPlato(
            @Valid @RequestBody CrearPlatoRequest request,
            @RequestHeader("idPropietario") Long idPropietario
    ) {
        PlatoResponse response = crearPlatoHandler.crearPlato(request, idPropietario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
