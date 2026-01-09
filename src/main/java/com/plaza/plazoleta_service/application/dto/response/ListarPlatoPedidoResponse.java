package com.plaza.plazoleta_service.application.dto.response;

public class ListarPlatoPedidoResponse {

    private Long platoId;
    private String nombre;
    private int cantidad;
    private Long precio;
    private String categoria;
    private String urlImagen;

    public ListarPlatoPedidoResponse(Long platoId, String nombre, int cantidad, Long precio,
                                     String categoria, String urlImagen) {
        this.platoId = platoId;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
    }

    public Long getPlatoId() { return platoId; }
    public void setPlatoId(Long platoId) { this.platoId = platoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Long getPrecio() { return precio; }
    public void setPrecio(Long precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
}
