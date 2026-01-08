package com.plaza.plazoleta_service.domain.model;

import com.plaza.plazoleta_service.application.dto.request.PlatoPedidoRequest;

import java.util.List;

public class PlatoPedido {
    private Long platoId;
    private String nombre;
    private int cantidad;
    private Long precio;
    private List<PlatoPedidoRequest> platos;


    public Long getPlatoId() { return platoId; }
    public void setPlatoId(Long platoId) { this.platoId = platoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public List<PlatoPedidoRequest> getPlatos() { return platos; }
    public void setPlatos(List<PlatoPedidoRequest> platos) { this.platos = platos; }

    public Long getPrecio() { return precio; }
    public void setPrecio(Long precio) { this.precio = precio; }

}
