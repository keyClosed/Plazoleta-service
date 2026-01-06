package com.plaza.plazoleta_service.domain;


import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.ListarPlatosUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarPlatosUseCaseTest {

    private IPlatoPersistencePort platoPersistencePort;
    private ListarPlatosUseCase listarPlatosUseCase;
    private Random random;

    @BeforeEach
    void setUp() {
        platoPersistencePort = mock(IPlatoPersistencePort.class);
        listarPlatosUseCase = new ListarPlatosUseCase(platoPersistencePort);
        random = new Random();
    }

    private Plato generarPlatoAleatorio(Long idRestaurante) {
        Plato plato = new Plato();
        plato.setId((long) (random.nextInt(1000) + 1));
        plato.setNombre("Plato-" + UUID.randomUUID().toString().substring(0, 5));
        plato.setPrecio(5000L + random.nextInt(50000));
        plato.setCategoria("Categoria-" + random.nextInt(5));
        plato.setUrlImagen("https://img.com/" + UUID.randomUUID().toString().substring(0, 5) + ".png");
        plato.setActivo(true);
        plato.setIdRestaurante(idRestaurante);
        return plato;
    }

    @Test
    void listarPlatos_Exitoso() {
        Long idRestaurante = 1L;
        int page = 0;
        int size = 5;
        String categoria = "Categoria-1";

        List<Plato> platos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            platos.add(generarPlatoAleatorio(idRestaurante));
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Plato> paginaMock = new PageImpl<>(platos, pageable, platos.size());

        when(platoPersistencePort.listarPlatos(
                eq(idRestaurante),
                eq(categoria),
                any(Pageable.class)
        )).thenReturn(paginaMock);

        Page<Plato> resultado =
                listarPlatosUseCase.listarPlatos(idRestaurante, page, size, categoria);

        assertNotNull(resultado);
        assertEquals(3, resultado.getContent().size());

        verify(platoPersistencePort, times(1))
                .listarPlatos(eq(idRestaurante), eq(categoria), any(Pageable.class));
    }

    @Test
    void listarPlatos_PaginaNegativa_Excepcion() {
        PlatoException ex = assertThrows(
                PlatoException.class,
                () -> listarPlatosUseCase.listarPlatos(1L, -1, 5, null)
        );

        assertEquals("La página no puede ser negativa", ex.getMessage());
    }

    @Test
    void listarPlatos_SizeInvalido_Excepcion() {
        PlatoException ex = assertThrows(
                PlatoException.class,
                () -> listarPlatosUseCase.listarPlatos(1L, 0, 0, null)
        );

        assertEquals("El tamaño de página debe ser mayor a 0", ex.getMessage());
    }
}