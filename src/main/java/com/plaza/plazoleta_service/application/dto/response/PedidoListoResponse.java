package com.plaza.plazoleta_service.application.dto.response;


public class PedidoListoResponse {

    private Long id;
    private String estado;
    private String telefonoCliente;
    private Integer pinSeguridad;
    private String mensaje;

    public PedidoListoResponse() {}

    public PedidoListoResponse(Long id, String estado, String telefonoCliente, Integer pinSeguridad, String mensaje) {
        this.id = id;
        this.estado = estado;
        this.telefonoCliente = telefonoCliente;
        this.pinSeguridad = pinSeguridad;
        this.mensaje = mensaje;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public Integer getPinSeguridad() { return pinSeguridad; }
    public void setPinSeguridad(Integer pinSeguridad) { this.pinSeguridad = pinSeguridad; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}