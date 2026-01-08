package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;
import com.plaza.plazoleta_service.application.handler.RealizarPedidoHandler;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('CLIENTE')")
@RequestMapping("/pedidos")
public class PedidoController {

    private final RealizarPedidoHandler handler;

    public PedidoController(RealizarPedidoHandler handler) {
        this.handler = handler;
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequest request) {
        try {
            PedidoResponse response = handler.crearPedido(request);
            return ResponseEntity.ok(response);
        } catch (PedidoException e) {

            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {

            return ResponseEntity.status(500).body("{\"error\": \"Error interno del servidor\"}");
        }
    }


}
