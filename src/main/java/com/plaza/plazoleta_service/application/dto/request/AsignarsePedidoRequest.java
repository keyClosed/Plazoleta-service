package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class AsignarsePedidoRequest {

    @NotNull(message = "El id del pedido es obligatorio")
    private Long idPedido;

    @NotNull(message = "El id del empleado es obligatorio")
    private Long idEmpleado;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}