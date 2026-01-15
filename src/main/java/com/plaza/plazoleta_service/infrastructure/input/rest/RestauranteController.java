package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;
import com.plaza.plazoleta_service.application.dto.response.ListarRestauranteResponse;
import com.plaza.plazoleta_service.application.dto.response.PageResponse;
import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.application.handler.CrearRestauranteHandler;
import com.plaza.plazoleta_service.application.handler.ListarRestaurantesHandler;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CrearRestauranteHandler crearRestauranteHandler;
    private final ListarRestaurantesHandler listarRestaurantesHandler;


    public RestauranteController(
            CrearRestauranteHandler crearRestauranteHandler,
            ListarRestaurantesHandler listarRestaurantesHandler
    ) {
        this.crearRestauranteHandler = crearRestauranteHandler;
        this.listarRestaurantesHandler = listarRestaurantesHandler;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestauranteResponse> crearRestaurante(
            @Valid @RequestBody CrearRestauranteRequest request) {

        RestauranteResponse response = crearRestauranteHandler.crearRestaurante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PageResponse<ListarRestauranteResponse>> listarRestaurantes(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<ListarRestauranteResponse> result =
                listarRestaurantesHandler.listar(page, size);

        PageResponse<ListarRestauranteResponse> response =
                new PageResponse<>(
                        result.getContent(),
                        result.getNumber(),
                        result.getSize(),
                        result.getTotalElements()
                );

        return ResponseEntity.ok(response);
    }

}


    //
    // - Obtener restaurante por ID
    // - Actualizar restaurante
    // - Eliminar restaurante

