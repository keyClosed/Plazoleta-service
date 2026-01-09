package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotNull;

 public class ListarPedidosRequest {

    @NotNull(message = "El id del empleado es obligatorio")
    private Long empleadoId;

    @NotNull(message = "El id del restaurante es obligatorio")
    private Long restauranteId;

    private String estado;

    private int pagina = 0;
    private int tamanio = 10;


    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getPagina() { return pagina; }
    public void setPagina(int pagina) { this.pagina = pagina; }

    public int getTamanio() { return tamanio; }
    public void setTamanio(int tamanio) { this.tamanio = tamanio; }
}