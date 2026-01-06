package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Plato;
import org.springframework.data.domain.Page;

public interface IListarPlatosService {

    Page<Plato> listarPlatos(Long idRestaurante, int page, int size, String categoria);
}