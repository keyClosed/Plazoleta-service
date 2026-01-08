package com.plaza.plazoleta_service.application.dto.response;

public class PlatoPedidoResponse {
    private Long platoId;
    private String nombre;
    private int cantidad;

    public Long getPlatoId() { return platoId; }
    public void setPlatoId(Long platoId) { this.platoId = platoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}