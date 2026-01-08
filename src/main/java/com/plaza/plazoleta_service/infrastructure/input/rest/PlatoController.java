package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.CambiarEstadoPlatoRequest;
import com.plaza.plazoleta_service.application.dto.request.CrearPlatoRequest;
import com.plaza.plazoleta_service.application.dto.request.ModificarPlatoRequest;
import com.plaza.plazoleta_service.application.dto.response.*;
import com.plaza.plazoleta_service.application.handler.CambiarEstadoPlatoHandler;
import com.plaza.plazoleta_service.application.handler.CrearPlatoHandler;
import com.plaza.plazoleta_service.application.handler.ModificarPlatoHandler;
import com.plaza.plazoleta_service.application.handler.impl.ListarPlatosHandlerImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platos")
public class PlatoController {

    private final CrearPlatoHandler crearPlatoHandler;
    private final ModificarPlatoHandler modificarPlatoHandler;
    private final CambiarEstadoPlatoHandler cambiarEstadoPlatoHandler;
    private final ListarPlatosHandlerImpl listarPlatosHandlerImpl;


    public PlatoController(
            CrearPlatoHandler crearPlatoHandler,
            ModificarPlatoHandler modificarPlatoHandler,
            CambiarEstadoPlatoHandler cambiarEstadoPlatoHandler,
            ListarPlatosHandlerImpl listarPlatosHandlerImpl) {
        this.crearPlatoHandler = crearPlatoHandler;
        this.modificarPlatoHandler = modificarPlatoHandler;
        this.cambiarEstadoPlatoHandler = cambiarEstadoPlatoHandler;
        this.listarPlatosHandlerImpl = listarPlatosHandlerImpl;
    }


    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping
    public ResponseEntity<PlatoResponse> crearPlato(
            @Valid @RequestBody CrearPlatoRequest request,
            @RequestHeader("idPropietario") Long idPropietario
    ) {
        PlatoResponse response = crearPlatoHandler.crearPlato(request, idPropietario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('PROPIETARIO')")
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

    @PatchMapping("/{idPlato}/estado")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<CambiarEstadoPlatoResponse> cambiarEstadoPlato(
            @PathVariable Long idPlato,
            @Valid @RequestBody CambiarEstadoPlatoRequest request,
            @RequestHeader("idPropietario") Long idPropietario
    ) {
        CambiarEstadoPlatoResponse response =
                cambiarEstadoPlatoHandler.cambiarEstadoPlato(
                        idPlato,
                        idPropietario,
                        request
                );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restaurante/{idRestaurante}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PageResponse<ListarPlatoResponse>> listarPlatos(
            @PathVariable Long idRestaurante,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String categoria
    ) {
        Page<ListarPlatoResponse> result =
                listarPlatosHandlerImpl.listar(idRestaurante, page, size, categoria);

        PageResponse<ListarPlatoResponse> response =
                new PageResponse<>(
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements()
                );

        return ResponseEntity.ok(response);
    }





}
