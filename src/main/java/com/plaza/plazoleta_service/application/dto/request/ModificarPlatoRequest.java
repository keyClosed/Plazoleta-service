package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ModificarPlatoRequest {

    @Positive(message = "El precio debe ser mayor a 0")
    private Long precio;

    @Size(min = 1, message = "La descripción no puede estar vacía")
    private String descripcion;

    public ModificarPlatoRequest() {
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
