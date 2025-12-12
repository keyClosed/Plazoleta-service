package com.plaza.plazoleta_service.application.dto.response;

public class RestauranteResponse {

    private long id;          //
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private long  idPropietario;

    public RestauranteResponse() {}

    public RestauranteResponse(long id, String nombre, String nit, String direccion,
                               String telefono, String urlLogo, long idPropietario) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlLogo = urlLogo;
        this.idPropietario = idPropietario;
    }

    // Getters y Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }

    public long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(long idPropietario) { this.idPropietario = idPropietario; }
}
