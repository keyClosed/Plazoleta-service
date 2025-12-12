package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;
import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.application.handler.CrearRestauranteHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CrearRestauranteHandler crearRestauranteHandler;

    public RestauranteController(CrearRestauranteHandler crearRestauranteHandler) {
        this.crearRestauranteHandler = crearRestauranteHandler;
    }

    // Crear restaurante
    @PostMapping
    public ResponseEntity<RestauranteResponse> crearRestaurante(
            @Valid @RequestBody CrearRestauranteRequest request) {

        RestauranteResponse response = crearRestauranteHandler.crearRestaurante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Aquí puedes agregar otros endpoints, por ejemplo:
    // - Listar restaurantes
    // - Obtener restaurante por ID
    // - Actualizar restaurante
    // - Eliminar restaurante
}
