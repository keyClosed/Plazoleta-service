package com.plaza.plazoleta_service.application.dto.response;

public class CambiarEstadoPlatoResponse {

    private Long id;
    private boolean activo;

    public CambiarEstadoPlatoResponse() {
    }

    public CambiarEstadoPlatoResponse(Long id, boolean activo) {
        this.id = id;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}