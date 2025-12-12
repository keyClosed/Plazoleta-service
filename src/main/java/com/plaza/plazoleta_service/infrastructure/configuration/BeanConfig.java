package com.plaza.plazoleta_service.infrastructure.configuration;

import com.plaza.plazoleta_service.application.mapper.RestauranteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig{

    // Mapper para convertir entre DTOs y modelo de dominio
    @Bean
    public RestauranteMapper restauranteMapper() {
        return new RestauranteMapper();
    }


}