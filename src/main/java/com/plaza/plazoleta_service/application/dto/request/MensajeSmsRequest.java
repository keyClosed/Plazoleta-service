package com.plaza.plazoleta_service.application.dto.request;

public class MensajeSmsRequest {
    private String telefono;
    private String mensaje;
    private Integer pin;

    public MensajeSmsRequest() {}

    public MensajeSmsRequest(String telefono, String mensaje, Integer pin) {
        this.telefono = telefono;
        this.mensaje = mensaje;
        this.pin = pin;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Integer getPin() { return pin; }
    public void setPin(Integer pin) { this.pin = pin; }
}