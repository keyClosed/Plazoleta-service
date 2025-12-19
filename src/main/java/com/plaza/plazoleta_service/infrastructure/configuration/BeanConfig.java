package com.plaza.plazoleta_service.infrastructure.configuration;

import com.plaza.plazoleta_service.application.mapper.RestauranteMapper;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.CrearPlatoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig{

    // Mapper para convertir entre DTOs y modelo de dominio
    @Bean
    public RestauranteMapper restauranteMapper() {
        return new RestauranteMapper();
    }
    @Bean
    public CrearPlatoUseCase crearPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new CrearPlatoUseCase(platoPersistencePort);
    }

}