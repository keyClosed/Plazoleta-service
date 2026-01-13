package com.plaza.plazoleta_service.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UsuarioResponse {
    private Long id;
    private String nombre;

    @JsonProperty("telefono")
    private String telefono;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}