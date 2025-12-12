package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CrearRestauranteRequest {

    @NotBlank(message = "El nombre del restaurante es obligatorio")
    @Pattern(regexp = ".*[a-zA-Z].*", message = "El nombre no puede ser solo números")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "\\d+", message = "El NIT debe ser solo números")
    private String nit;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "El teléfono debe ser numérico y puede contener máximo 13 caracteres, opcional '+'")
    private String telefono;

    @NotBlank(message = "La URL del logo es obligatoria")
    private String urlLogo;

    @NotNull(message = "El id del propietario es obligatorio")
    private long idPropietario;


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlLogo() {
        return urlLogo;
    }
    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public long getIdPropietario() {
        return idPropietario;
    }
    public void setIdPropietario(long idPropietario) {
        this.idPropietario = idPropietario;
    }
}