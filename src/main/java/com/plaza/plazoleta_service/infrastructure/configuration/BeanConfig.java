package com.plaza.plazoleta_service.infrastructure.configuration;

import com.plaza.plazoleta_service.application.mapper.RestauranteMapper;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import com.plaza.plazoleta_service.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig{


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
    @Bean
    public ModificarPlatoUseCase modificarPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new ModificarPlatoUseCase(platoPersistencePort);
    }

    @Bean
    public CambiarEstadoPlatoUseCase cambiarEstadoPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new CambiarEstadoPlatoUseCase(platoPersistencePort);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(
            IRestaurantePersistencePort restaurantePersistencePort
    ) {
        return new ListarRestaurantesUseCase(restaurantePersistencePort);
    }
    @Bean
    public ListarPlatosUseCase listarPlatosUseCase(IPlatoPersistencePort platoPersistencePort) {
        return new ListarPlatosUseCase(platoPersistencePort);
    }
}
