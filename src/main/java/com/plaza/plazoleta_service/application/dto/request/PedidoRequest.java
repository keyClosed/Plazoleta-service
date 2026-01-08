package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class PedidoRequest {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El id del restaurante es obligatorio")
    private Long restauranteId;

    @NotNull(message = "La lista de platos no puede ser vacía")
    private List<PlatoPedidoRequest> platos;

    // Getters y setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public List<PlatoPedidoRequest> getPlatos() { return platos; }
    public void setPlatos(List<PlatoPedidoRequest> platos) { this.platos = platos; }
}