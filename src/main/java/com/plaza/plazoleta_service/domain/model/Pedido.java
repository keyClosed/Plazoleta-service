package com.plaza.plazoleta_service.domain.model;

import java.util.List;

public class Pedido {
    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private List<PlatoPedido> platos;
    private String estado;
    private Long empleadoAsignadoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public List<PlatoPedido> getPlatos() { return platos; }
    public void setPlatos(List<PlatoPedido> platos) { this.platos = platos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getEmpleadoAsignadoId() { return empleadoAsignadoId; }
    public void setEmpleadoAsignadoId(Long empleadoAsignadoId) { this.empleadoAsignadoId = empleadoAsignadoId; }
}