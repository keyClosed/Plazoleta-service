package com.plaza.plazoleta_service.domain.model;

public class Plato {

    private Long id;
    private String nombre;
    private String descripcion;
    private Long precio;
    private String urlImagen;
    private String categoria;
    private Long idRestaurante;
    private Long idPropietario;
    private boolean activo;

    public Plato() {}

    // getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getPrecio() {
        return precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public Long getIdPropietario() {
        return idPropietario;
    }

    public boolean isActivo() {
        return activo;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public void setIdPropietario(Long idPropietario) {
        this.idPropietario = idPropietario;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}