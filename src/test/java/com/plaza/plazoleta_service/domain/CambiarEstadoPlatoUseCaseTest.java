package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.CambiarEstadoPlatoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CambiarEstadoPlatoUseCaseTest {

    private IPlatoPersistencePort platoPersistencePort;
    private CambiarEstadoPlatoUseCase cambiarEstadoPlatoUseCase;
    private Random random;

    @BeforeEach
    void setUp() {
        platoPersistencePort = mock(IPlatoPersistencePort.class);
        cambiarEstadoPlatoUseCase = new CambiarEstadoPlatoUseCase(platoPersistencePort);
        random = new Random();
    }

    private Plato generarPlatoAleatorio() {
        Plato plato = new Plato();
        plato.setId((long) (random.nextInt(1000) + 1));
        plato.setIdRestaurante((long) (random.nextInt(100) + 1));
        plato.setIdPropietario((long) (random.nextInt(100) + 1));
        plato.setNombre("Plato-" + random.nextInt(1000));
        plato.setDescripcion("Desc-" + random.nextInt(1000));
        plato.setPrecio((long) (random.nextInt(50000) + 1));
        plato.setUrlImagen("http://img.com/" + random.nextInt(1000) + ".jpg");
        plato.setCategoria("Cat-" + random.nextInt(100));
        plato.setActivo(true);
        return plato;
    }

    @Test
    void cambiarEstadoPlato_Exitoso() {
        Plato plato = generarPlatoAleatorio();

        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.of(plato));
        when(platoPersistencePort.esPropietarioDelRestaurante(plato.getIdPropietario(), plato.getIdRestaurante())).thenReturn(true);
        when(platoPersistencePort.guardarPlato(any())).thenAnswer(i -> i.getArgument(0));

        // Cambiar estado
        boolean nuevoEstado = !plato.isActivo();
        Plato resultado = cambiarEstadoPlatoUseCase.cambiarEstadoPlato(plato.getId(), plato.getIdPropietario(), nuevoEstado);

        assertNotNull(resultado);
        assertEquals(nuevoEstado, resultado.isActivo());
        verify(platoPersistencePort, times(1)).guardarPlato(any());
    }

    @Test
    void cambiarEstadoPlato_PlatoNoExiste_LanzaExcepcion() {
        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.empty());

        PlatoException ex = assertThrows(PlatoException.class, () ->
                cambiarEstadoPlatoUseCase.cambiarEstadoPlato(1L, 1L, false)
        );

        assertEquals("El plato no existe", ex.getMessage());
    }

    @Test
    void cambiarEstadoPlato_NoEsPropietario_LanzaExcepcion() {
        Plato plato = generarPlatoAleatorio();

        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.of(plato));
        when(platoPersistencePort.esPropietarioDelRestaurante(anyLong(), anyLong())).thenReturn(false);

        PlatoException ex = assertThrows(PlatoException.class, () ->
                cambiarEstadoPlatoUseCase.cambiarEstadoPlato(plato.getId(), 999L, false)
        );

        assertEquals("Solo el propietario puede habilitar o deshabilitar el plato", ex.getMessage());
    }
}