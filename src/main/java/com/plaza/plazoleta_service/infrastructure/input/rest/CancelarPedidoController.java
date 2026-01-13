package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.response.CancelarPedidoResponse;
import com.plaza.plazoleta_service.application.handler.CancelarPedidoHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class CancelarPedidoController {

    private final CancelarPedidoHandler cancelarPedidoHandler;

    public CancelarPedidoController(CancelarPedidoHandler cancelarPedidoHandler) {
        this.cancelarPedidoHandler = cancelarPedidoHandler;
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/{pedidoId}/cancelar")
    public ResponseEntity<CancelarPedidoResponse> cancelarPedido(
            @PathVariable Long pedidoId) {

        CancelarPedidoResponse response =
                cancelarPedidoHandler.ejecutar(pedidoId);

        return ResponseEntity.ok(response);
    }
}

