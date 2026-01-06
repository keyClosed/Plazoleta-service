package com.plaza.plazoleta_service.application.dto.response;

public class ListarPlatoResponse {

    private String nombre;
    private Long precio; // Cambiado a Long
    private String categoria;
    private String urlImagen;

    public ListarPlatoResponse(String nombre, Long precio, String categoria, String urlImagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}