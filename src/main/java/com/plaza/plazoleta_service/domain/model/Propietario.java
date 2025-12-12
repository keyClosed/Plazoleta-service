package com.plaza.plazoleta_service.domain.model;

public class Propietario {

    private String id;
    private String nombre;
    private String rol;

    public Propietario(String id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getRol() { return rol; }

    // Setters (opcional si necesitas modificar)
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setRol(String rol) { this.rol = rol; }
}
