package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.spi.EmpleadoRestaurantePersistencePort;
import com.plaza.plazoleta_service.infrastructure.out.jpa.repository.EmpleadoRepository;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoRestauranteAdapter implements EmpleadoRestaurantePersistencePort {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoRestauranteAdapter(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Long obtenerRestauranteIdPorEmpleado(Long idEmpleado) {
        return empleadoRepository.obtenerRestauranteId(idEmpleado);
    }
}