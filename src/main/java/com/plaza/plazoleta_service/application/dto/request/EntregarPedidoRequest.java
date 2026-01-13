package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class EntregarPedidoRequest {

    @NotNull(message = "El PIN es obligatorio")
    private Integer pin;

    public EntregarPedidoRequest() {}

    public EntregarPedidoRequest(Integer pin) {
        this.pin = pin;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}