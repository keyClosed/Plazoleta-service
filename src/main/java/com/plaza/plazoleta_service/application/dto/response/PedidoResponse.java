package com.plaza.plazoleta_service.application.dto.response;

import java.util.List;

public class PedidoResponse {
    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private List<PlatoPedidoResponse> platos; // ← muy importante

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<PlatoPedidoResponse> getPlatos() { return platos; }
    public void setPlatos(List<PlatoPedidoResponse> platos) { this.platos = platos; }
}