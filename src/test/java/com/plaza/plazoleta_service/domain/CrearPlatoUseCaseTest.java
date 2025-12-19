package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.usecase.CrearPlatoUseCase;
import com.plaza.plazoleta_service.domain.exception.PlatoException;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrearPlatoUseCaseTest {

    private IPlatoPersistencePort platoPersistencePort;
    private CrearPlatoUseCase crearPlatoUseCase;
    private Random random;

    @BeforeEach
    void setUp() {
        platoPersistencePort = mock(IPlatoPersistencePort.class);
        crearPlatoUseCase = new CrearPlatoUseCase(platoPersistencePort);
        random = new Random();
    }

    // Genera un Plato con todos los campos aleatorios
    private Plato generarPlatoAleatorio(Long idRestaurante, Long idPropietario) {
        Plato plato = new Plato();
        plato.setNombre("Plato-" + UUID.randomUUID().toString().substring(0, 5));
        plato.setDescripcion("Descripcion-" + UUID.randomUUID().toString().substring(0, 5));
        plato.setPrecio((long) (random.nextInt(100000) + 1)); // >0
        plato.setUrlImagen("http://img.com/" + UUID.randomUUID() + ".jpg");
        plato.setCategoria("Categoria-" + UUID.randomUUID().toString().substring(0, 3));
        plato.setIdRestaurante(idRestaurante);
        plato.setIdPropietario(idPropietario);
        return plato;
    }

    // id aleatorio para propietario o restaurante
    private Long generarIdAleatorio() {
        return (long) (random.nextInt(1000) + 1); // entre 1 y 1000
    }

    @Test
    void crearPlato_Exitoso() {
        Long idRestaurante = generarIdAleatorio();
        Long idPropietario = generarIdAleatorio();
        Plato plato = generarPlatoAleatorio(idRestaurante, idPropietario);

        when(platoPersistencePort.existeRestaurante(idRestaurante)).thenReturn(true);
        when(platoPersistencePort.esPropietarioDelRestaurante(idPropietario, idRestaurante)).thenReturn(true);
        when(platoPersistencePort.guardarPlato(any())).thenAnswer(i -> i.getArgument(0));

        Plato resultado = crearPlatoUseCase.crearPlato(plato, idPropietario);

        assertNotNull(resultado);
        assertTrue(resultado.isActivo());
        verify(platoPersistencePort, times(1)).guardarPlato(any());
    }

    @Test
    void crearPlato_FaltanCampos_Excepcion() {
        Plato plato = new Plato();
        Long idPropietario = generarIdAleatorio();

        PlatoException ex = assertThrows(PlatoException.class,
                () -> crearPlatoUseCase.crearPlato(plato, idPropietario));
        assertTrue(ex.getMessage().contains("El nombre del plato es obligatorio"));
    }

    @Test
    void crearPlato_RestauranteNoExiste_Excepcion() {
        Long idRestaurante = generarIdAleatorio();
        Long idPropietario = generarIdAleatorio();
        Plato plato = generarPlatoAleatorio(idRestaurante, idPropietario);

        when(platoPersistencePort.existeRestaurante(idRestaurante)).thenReturn(false);

        PlatoException ex = assertThrows(PlatoException.class,
                () -> crearPlatoUseCase.crearPlato(plato, idPropietario));

        assertEquals("El restaurante no existe", ex.getMessage());
    }

    @Test
    void crearPlato_PropietarioIncorrecto_Excepcion() {
        Long idRestaurante = generarIdAleatorio();
        Long idPropietario = generarIdAleatorio();
        Plato plato = generarPlatoAleatorio(idRestaurante, idPropietario);

        when(platoPersistencePort.existeRestaurante(idRestaurante)).thenReturn(true);
        when(platoPersistencePort.esPropietarioDelRestaurante(idPropietario, idRestaurante)).thenReturn(false);

        PlatoException ex = assertThrows(PlatoException.class,
                () -> crearPlatoUseCase.crearPlato(plato, idPropietario));

        assertEquals("Solo el propietario del restaurante puede crear platos", ex.getMessage());
    }
}