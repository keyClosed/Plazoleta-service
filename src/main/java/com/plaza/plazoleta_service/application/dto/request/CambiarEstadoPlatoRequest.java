package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class CambiarEstadoPlatoRequest {

    @NotNull(message = "El estado del plato es obligatorio")
    private Boolean activo;

    public CambiarEstadoPlatoRequest() {
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
