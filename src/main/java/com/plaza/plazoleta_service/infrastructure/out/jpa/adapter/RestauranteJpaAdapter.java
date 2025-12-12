package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import com.plaza.plazoleta_service.infrastructure.out.jpa.entity.RestauranteEntity;
import com.plaza.plazoleta_service.infrastructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plaza.plazoleta_service.infrastructure.out.jpa.repository.RestauranteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {

    private final RestauranteRepository restauranteRepository;
    private final RestauranteEntityMapper restauranteEntityMapper;

    public RestauranteJpaAdapter(RestauranteRepository restauranteRepository,
                                 RestauranteEntityMapper restauranteEntityMapper) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteEntityMapper = restauranteEntityMapper;
    }

    @Override
    public Restaurante guardarRestaurante(Restaurante restaurante) {
        RestauranteEntity entity = restauranteEntityMapper.restauranteToEntity(restaurante);
        RestauranteEntity savedEntity = restauranteRepository.save(entity);
        return restauranteEntityMapper.entityToRestaurante(savedEntity);
    }

    @Override
    public Restaurante obtenerRestaurantePorId(Long id) {
        RestauranteEntity entity = restauranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado"));
        return restauranteEntityMapper.entityToRestaurante(entity);
    }

    @Override
    public List<Restaurante> listarRestaurantes() {
        List<RestauranteEntity> entities = restauranteRepository.findAll();
        return entities.stream()
                .map(restauranteEntityMapper::entityToRestaurante)
                .collect(Collectors.toList());
    }
}