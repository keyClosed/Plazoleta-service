package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.PlatoEntity;
import com.plaza.plazoleta_service.infrastructure.out.jpa.mapper.PlatoEntityMapper;
import com.plaza.plazoleta_service.infrastructure.out.jpa.repository.PlatoRepository;
import com.plaza.plazoleta_service.infrastructure.out.jpa.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

@Component
public class PlatoJpaAdapter implements IPlatoPersistencePort {

    private final PlatoRepository platoRepository;
    private final RestauranteRepository restauranteRepository;
    private final PlatoEntityMapper platoEntityMapper;

    public PlatoJpaAdapter(
            PlatoRepository platoRepository,
            RestauranteRepository restauranteRepository,
            PlatoEntityMapper platoEntityMapper
    ) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
        this.platoEntityMapper = platoEntityMapper;
    }

    @Override
    public Plato guardarPlato(Plato plato) {
        PlatoEntity entity = platoEntityMapper.toEntity(plato);
        PlatoEntity saved = platoRepository.save(entity);
        return platoEntityMapper.toModel(saved);
    }

    @Override
    public boolean existeRestaurante(Long idRestaurante) {
        return restauranteRepository.existsById(idRestaurante);
    }

    @Override
    public boolean esPropietarioDelRestaurante(Long idPropietario, Long idRestaurante) {
        return restauranteRepository.existsByIdAndIdPropietario(idRestaurante, idPropietario);
    }
}
