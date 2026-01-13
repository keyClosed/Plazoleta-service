package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.request.PedidoRequest;
import com.plaza.plazoleta_service.application.dto.response.PedidoResponse;
import com.plaza.plazoleta_service.application.handler.RealizarPedidoHandler;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.infrastructure.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('CLIENTE')")
@RequestMapping("/pedidos")
public class PedidoController {

    private final RealizarPedidoHandler handler;
    private final JwtUtil jwtUtil;

    public PedidoController(RealizarPedidoHandler handler, JwtUtil jwtUtil) {
        this.handler = handler;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> crearPedido(
            @RequestBody PedidoRequest request,
            @RequestHeader("Authorization") String token
    ) {
        try {
            String jwtToken = token.substring(7);
            Long clienteId = jwtUtil.getUserIdFromToken(jwtToken);

            System.out.println("Cliente autenticado: " + clienteId);

            PedidoResponse response = handler.crearPedido(request, clienteId, token);

            return ResponseEntity.ok(response);

        } catch (PedidoException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}