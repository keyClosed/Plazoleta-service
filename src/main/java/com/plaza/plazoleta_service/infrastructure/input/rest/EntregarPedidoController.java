package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.EntregarPedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.EntregarPedidoResponse;
import com.plaza.plazoleta_service.application.handler.EntregarPedidoHandler;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.usecase.EntregarPedidoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class EntregarPedidoController {

    private final EntregarPedidoHandler entregarPedidoHandler;

    public EntregarPedidoController(EntregarPedidoHandler entregarPedidoHandler) {
        this.entregarPedidoHandler = entregarPedidoHandler;
    }

    @PreAuthorize("hasRole('EMPLEADO')")
    @PostMapping("/{pedidoId}/entregar")
    public ResponseEntity<EntregarPedidoResponse> entregarPedido(
            @PathVariable Long pedidoId,
            @RequestBody @Valid EntregarPedidoRequest request) {

        EntregarPedidoResponse response =
                entregarPedidoHandler.ejecutar(pedidoId, request);

        return ResponseEntity.ok(response);
    }
}
