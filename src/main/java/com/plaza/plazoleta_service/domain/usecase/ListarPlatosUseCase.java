package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IListarPlatosService;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ListarPlatosUseCase implements IListarPlatosService {

    private final IPlatoPersistencePort platoPersistencePort;

    public ListarPlatosUseCase(IPlatoPersistencePort platoPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
    }


    @Override
    public Page<Plato> listarPlatos(Long idRestaurante, int page, int size, String categoria) {

        if (page < 0) {
            throw new IllegalArgumentException("La página no puede ser negativa");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño de página debe ser mayor a 0");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        return platoPersistencePort.listarPlatos(idRestaurante, categoria, pageable);
    }
}
