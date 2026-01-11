package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.spi.EmpleadoRestaurantePersistencePort;
import com.plaza.plazoleta_service.infrastructure.out.jpa.feign.EmpleadoFeignClient;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoRestauranteFeignAdapter
        implements EmpleadoRestaurantePersistencePort {

    private final EmpleadoFeignClient empleadoFeignClient;

    public EmpleadoRestauranteFeignAdapter(
            EmpleadoFeignClient empleadoFeignClient
    ) {
        this.empleadoFeignClient = empleadoFeignClient;
    }

    @Override
    public boolean empleadoPerteneceARestaurante(
            Long idEmpleado,
            Long idRestaurante
    ) {
        Long restauranteEmpleado =
                empleadoFeignClient.obtenerRestaurantePorEmpleado(idEmpleado);

        return restauranteEmpleado != null
                && restauranteEmpleado.equals(idRestaurante);
    }
}
