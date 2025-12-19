package com.plaza.plazoleta_service.application.dto.response;

public class ModificarPlatoResponse {

    private Long id;
    private Long precio;
    private String descripcion;
    private boolean activo;

    public ModificarPlatoResponse() {
    }

    public ModificarPlatoResponse(Long id, Long precio, String descripcion, boolean activo) {
        this.id = id;
        this.precio = precio;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public Long getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
