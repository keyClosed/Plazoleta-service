package com.plaza.plazoleta_service.application.dto.response;

public class CancelarPedidoResponse {

    private Long id;
    private String estado;
    private String mensaje;

    public CancelarPedidoResponse() {}

    public CancelarPedidoResponse(Long id, String estado, String mensaje) {
        this.id = id;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
