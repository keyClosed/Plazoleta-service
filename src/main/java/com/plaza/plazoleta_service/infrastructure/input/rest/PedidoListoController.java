package com.plaza.plazoleta_service.infrastructure.input.rest;



import com.plaza.plazoleta_service.domain.usecase.PedidoListoUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoListoController {

    private final PedidoListoUseCase pedidoListoUseCase;

    public PedidoListoController(PedidoListoUseCase pedidoListoUseCase) {
        this.pedidoListoUseCase = pedidoListoUseCase;
    }

    @PostMapping("/{pedidoId}/notificar-listo")
    public ResponseEntity<String> notificarPedidoListo(@PathVariable Long pedidoId) {
        try {
            pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
            return ResponseEntity.ok("Pedido notificado como LISTO.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al notificar pedido: " + e.getMessage());
        }
    }
}