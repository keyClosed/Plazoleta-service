package com.plaza.plazoleta_service.application.dto.response;

public class AsignarsePedidoResponse {

    private Long idPedido;
    private Long empleadoAsignado;
    private String estado;

    public AsignarsePedidoResponse(Long idPedido, Long empleadoAsignado, String estado) {
        this.idPedido = idPedido;
        this.empleadoAsignado = empleadoAsignado;
        this.estado = estado;
    }


    public Long getIdPedido() {
        return idPedido;
    }

    public Long getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public String getEstado() {
        return estado;
    }
}
