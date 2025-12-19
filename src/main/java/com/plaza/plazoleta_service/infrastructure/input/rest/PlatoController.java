package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.request.ModificarPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.ModificarPlatoResponse;
import com.plaza.plazoleta_service.application.dto.response.PlatoResponse;
import com.plaza.plazoleta_service.application.handler.CrearPlatoHandler;
import com.plaza.plazoleta_service.application.handler.ModificarPlatoHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final CrearPlatoHandler crearPlatoHandler;
    private final ModificarPlatoHandler modificarPlatoHandler;

    public PlatoController(
            CrearPlatoHandler crearPlatoHandler,
            ModificarPlatoHandler modificarPlatoHandler
    ) {
        this.crearPlatoHandler = crearPlatoHandler;
        this.modificarPlatoHandler = modificarPlatoHandler;
    }

    @PostMapping
    public ResponseEntity<PlatoResponse> crearPlato(
            @Valid @RequestBody CrearPlatoRequest request,
            @RequestHeader("idPropietario") Long idPropietario
    ) {
        PlatoResponse response = crearPlatoHandler.crearPlato(request, idPropietario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlato}")
    public ResponseEntity<ModificarPlatoResponse> modificarPlato(
            @PathVariable Long idPlato,
            @Valid @RequestBody ModificarPlatoRequest request,
            @RequestHeader("idPropietario") Long idPropietario
    ) {
        ModificarPlatoResponse response = modificarPlatoHandler.modificarPlato(
                idPlato,
                idPropietario,
                request
        );
        return ResponseEntity.ok(response);
    }
}
