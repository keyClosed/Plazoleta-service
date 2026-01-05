package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.RestauranteException;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import com.plaza.plazoleta_service.domain.usecase.ListarRestaurantesUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarRestaurantesUseCaseTest {

    private IRestaurantePersistencePort restaurantePersistencePort;
    private ListarRestaurantesUseCase listarRestaurantesUseCase;
    private Random random;

    @BeforeEach
    void setUp() {
        restaurantePersistencePort = mock(IRestaurantePersistencePort.class);
        listarRestaurantesUseCase = new ListarRestaurantesUseCase(restaurantePersistencePort);
        random = new Random();
    }


    private Restaurante generarRestauranteAleatorio() {
        Restaurante restaurante = new Restaurante();
        restaurante.setId((long) (random.nextInt(1000) + 1));
        restaurante.setNombre("Restaurante-" + UUID.randomUUID().toString().substring(0, 5));
        restaurante.setNit(String.valueOf(10000000 + random.nextInt(90000000)));
        restaurante.setDireccion("Calle " + (random.nextInt(999) + 1));
        restaurante.setTelefono("+57" + (100000000 + random.nextInt(900000000)));
        restaurante.setUrlLogo("https://logo.com/" + UUID.randomUUID().toString().substring(0, 5) + ".png");
        restaurante.setIdPropietario((long) (random.nextInt(100) + 1));
        return restaurante;
    }

    @Test
    void listarRestaurantes_Exitoso() {
        int page = 0;
        int size = 5;

        List<Restaurante> restaurantes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            restaurantes.add(generarRestauranteAleatorio());
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Restaurante> paginaMock = new PageImpl<>(restaurantes, pageable, restaurantes.size());

        when(restaurantePersistencePort.listarRestaurantes(pageable)).thenReturn(paginaMock);

        Page<Restaurante> resultado = listarRestaurantesUseCase.listarRestaurantes(page, size);

        assertNotNull(resultado);
        assertEquals(3, resultado.getContent().size());
        assertEquals(restaurantes.get(0).getNombre(), resultado.getContent().get(0).getNombre());

        verify(restaurantePersistencePort, times(1)).listarRestaurantes(pageable);
    }

    @Test
    void listarRestaurantes_PaginaNegativa_Excepcion() {
        RestauranteException ex = assertThrows(RestauranteException.class,
                () -> listarRestaurantesUseCase.listarRestaurantes(-1, 5));
        assertEquals("La página no puede ser negativa", ex.getMessage());
    }

    @Test
    void listarRestaurantes_SizeInvalido_Excepcion() {
        RestauranteException ex = assertThrows(RestauranteException.class,
                () -> listarRestaurantesUseCase.listarRestaurantes(0, 0));
        assertEquals("El tamaño de página debe ser mayor a 0", ex.getMessage());
    }
}