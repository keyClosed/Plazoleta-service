package com.plaza.plazoleta_service.domain;
import com.plaza.plazoleta_service.domain.usecase.ModificarPlatoUseCase;
import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ModificarPlatoUseCaseTest {

    private IPlatoPersistencePort platoPersistencePort;
    private ModificarPlatoUseCase useCase;
    private Random random;

    @BeforeEach
    void setUp() {
        platoPersistencePort = mock(IPlatoPersistencePort.class);
        useCase = new ModificarPlatoUseCase(platoPersistencePort);
        random = new Random();
    }

    private Plato generarPlatoRandom() {
        Plato plato = new Plato();
        plato.setId((long) (random.nextInt(1000) + 1));
        plato.setIdRestaurante((long) (random.nextInt(100) + 1));
        plato.setIdPropietario((long) (random.nextInt(100) + 1));
        plato.setDescripcion("Desc-" + random.nextInt(1000));
        plato.setPrecio((long) (random.nextInt(50000) + 1));
        plato.setActivo(true);
        return plato;
    }

    @Test
    void modificarPlato_platoNoExiste_lanzaExcepcion() {
        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.empty());

        PlatoException exception = assertThrows(PlatoException.class, () ->
                useCase.modificarPlato(1L, 1L, "Nueva Desc", 1000L)
        );

        assertEquals("El plato no existe", exception.getMessage());
    }

    @Test
    void modificarPlato_noEsPropietario_lanzaExcepcion() {
        Plato plato = generarPlatoRandom();
        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.of(plato));
        when(platoPersistencePort.esPropietarioDelRestaurante(anyLong(), anyLong())).thenReturn(false);

        PlatoException exception = assertThrows(PlatoException.class, () ->
                useCase.modificarPlato(plato.getId(), 999L, "Nueva Desc", 1000L)
        );

        assertEquals("Solo el propietario puede modificar el plato", exception.getMessage());
    }

    @Test
    void modificarPlato_precioInvalido_lanzaExcepcion() {
        Plato plato = generarPlatoRandom();
        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.of(plato));
        when(platoPersistencePort.esPropietarioDelRestaurante(anyLong(), anyLong())).thenReturn(true);

        PlatoException exception = assertThrows(PlatoException.class, () ->
                useCase.modificarPlato(plato.getId(), plato.getIdPropietario(), "Desc", 0L)
        );

        assertEquals("El precio debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void modificarPlato_exitoso() {
        Plato plato = generarPlatoRandom();
        when(platoPersistencePort.obtenerPlatoPorId(anyLong())).thenReturn(Optional.of(plato));
        when(platoPersistencePort.esPropietarioDelRestaurante(anyLong(), anyLong())).thenReturn(true);
        when(platoPersistencePort.guardarPlato(any())).thenAnswer(invocation -> invocation.getArgument(0));

        String nuevaDesc = "Descripcion Actualizada";
        Long nuevoPrecio = plato.getPrecio() + 1000;

        Plato modificado = useCase.modificarPlato(plato.getId(), plato.getIdPropietario(), nuevaDesc, nuevoPrecio);

        assertEquals(nuevaDesc, modificado.getDescripcion());
        assertEquals(nuevoPrecio, modificado.getPrecio());
        assertTrue(modificado.isActivo());

        verify(platoPersistencePort, times(1)).guardarPlato(modificado);
    }
}