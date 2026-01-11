package com.plaza.plazoleta_service.infrastructure.input.rest;

import com.plaza.plazoleta_service.application.dto.response.ListarPedidosResponse;
import com.plaza.plazoleta_service.application.dto.response.PageResponse;
import com.plaza.plazoleta_service.application.handler.ListarPedidosHandler;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/empleado")
@PreAuthorize("hasRole('EMPLEADO')")
public class PedidoEmpleadoController {

    private final ListarPedidosHandler listarPedidosHandler;

    public PedidoEmpleadoController(ListarPedidosHandler listarPedidosHandler) {
        this.listarPedidosHandler = listarPedidosHandler;
    }

    @GetMapping("/restaurante/{idRestaurante}")
    public ResponseEntity<PageResponse<ListarPedidosResponse>> listarPedidosPorEstado(
            @PathVariable Long idRestaurante,
            @RequestParam String estado,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<ListarPedidosResponse> pedidos = listarPedidosHandler
                .listarPedidosPorEstado(idRestaurante, estado, page, size);

        PageResponse<ListarPedidosResponse> response = new PageResponse<>(
                pedidos.getContent(),
                pedidos.getNumber(),
                pedidos.getSize(),
                pedidos.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }
}