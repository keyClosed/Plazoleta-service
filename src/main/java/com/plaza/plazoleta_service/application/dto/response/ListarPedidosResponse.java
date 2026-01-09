package com.plaza.plazoleta_service.application.dto.response;

import java.util.List;

public class ListarPedidosResponse {

    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private List<ListarPlatoPedidoResponse> platos;

    public ListarPedidosResponse() {}

    public ListarPedidosResponse(Long id, Long clienteId, Long restauranteId, String estado, List<ListarPlatoPedidoResponse> platos) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.estado = estado;
        this.platos = platos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<ListarPlatoPedidoResponse> getPlatos() { return platos; }
    public void setPlatos(List<ListarPlatoPedidoResponse> platos) { this.platos = platos; }
}