package com.plaza.plazoleta_service.domain.api;

import com.plaza.plazoleta_service.domain.model.Restaurante;
import org.springframework.data.domain.Page;

public interface IListarRestaurantesService {

    Page<Restaurante> listarRestaurantes(int page, int size);
}
