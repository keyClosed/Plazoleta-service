package com.plaza.plazoleta_service.application.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;


public class PedidoRequest {

    @NotNull(message = "El restaurante es obligatorio")
    private Long restauranteId;

    @NotNull(message = "Debe incluir al menos un plato")
    private List<PlatoPedidoRequest> platos;

    private Long total;

    public PedidoRequest() {}

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public List<PlatoPedidoRequest> getPlatos() {
        return platos;
    }

    public void setPlatos(List<PlatoPedidoRequest> platos) {
        this.platos = platos;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}