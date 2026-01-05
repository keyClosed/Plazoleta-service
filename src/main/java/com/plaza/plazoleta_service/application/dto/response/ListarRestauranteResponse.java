package com.plaza.plazoleta_service.application.dto.response;

public class ListarRestauranteResponse {

    private String nombre;
    private String urlLogo;

    public ListarRestauranteResponse() {
    }

    public ListarRestauranteResponse(String nombre, String urlLogo) {
        this.nombre = nombre;
        this.urlLogo = urlLogo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
