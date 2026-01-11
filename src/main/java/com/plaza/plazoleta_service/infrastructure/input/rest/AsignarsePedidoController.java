package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.AsignarsePedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.AsignarsePedidoResponse;
import com.plaza.plazoleta_service.application.handler.AsignarsePedidoHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/empleado")
@PreAuthorize("hasRole('EMPLEADO')")
public class AsignarsePedidoController {

    private final AsignarsePedidoHandler asignarsePedidoHandler;

    public AsignarsePedidoController(AsignarsePedidoHandler asignarsePedidoHandler) {
        this.asignarsePedidoHandler = asignarsePedidoHandler;
    }

    @PatchMapping("/asignar")
    public ResponseEntity<AsignarsePedidoResponse> asignarsePedido(
            @RequestBody AsignarsePedidoRequest request
    ) {

        AsignarsePedidoResponse response = asignarsePedidoHandler.asignarsePedido(request);
        return ResponseEntity.ok(response);
    }
}