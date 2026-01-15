package com.plaza.plazoleta_service.domain.spi;

public interface ITrazabilidadClientPort {
    void registrarCambioEstado(Long pedidoId, Long clienteId, String estado, String observaciones);
}
