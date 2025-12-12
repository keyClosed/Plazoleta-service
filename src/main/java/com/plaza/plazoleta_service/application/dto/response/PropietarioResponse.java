package com.plaza.plazoleta_service.application.dto.response;

public class PropietarioResponse {
    private long id;
    private String nombre;
    private String rol;

    // Getters y setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
