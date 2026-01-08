package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.Plato;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.RealizarPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RealizarPedidoUseCaseTest {

    private IPedidoPersistencePort pedidoPersistencePort;
    private IPlatoPersistencePort platoPersistencePort;
    private RealizarPedidoUseCase realizarPedidoUseCase;

    @BeforeEach
    void setUp() {
        pedidoPersistencePort = mock(IPedidoPersistencePort.class);
        platoPersistencePort = mock(IPlatoPersistencePort.class);
        realizarPedidoUseCase = new RealizarPedidoUseCase(pedidoPersistencePort, platoPersistencePort);
    }

    // Generador de IDs aleatorios
    private long randomId() {
        return new Random().nextInt(1000) + 1;
    }

    // Generador de nombres aleatorios
    private String randomNombre() {
        String[] nombres = {"Hamburguesa Doble", "Lasaña", "Pizza Pepperoni", "Ensalada César"};
        return nombres[new Random().nextInt(nombres.length)];
    }

    // Generador de precio aleatorio
    private long randomPrecio() {
        return (new Random().nextInt(50) + 1) * 1000; // entre 1000 y 50000
    }

    @Test
    void crearPedidoAleatorioExitoso() {
        // Datos aleatorios
        long clienteId = randomId();
        long restauranteId = randomId();
        long platoId1 = randomId();
        long platoId2 = randomId();

        // Pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);

        PlatoPedido plato1 = new PlatoPedido();
        plato1.setPlatoId(platoId1);
        plato1.setCantidad(2);

        PlatoPedido plato2 = new PlatoPedido();
        plato2.setPlatoId(platoId2);
        plato2.setCantidad(1);

        pedido.setPlatos(Arrays.asList(plato1, plato2));

        // Mock: cliente no tiene pedidos en proceso
        when(pedidoPersistencePort.tienePedidoEnProceso(clienteId)).thenReturn(false);

        // Mock: los platos existen en BD
        Plato platoBD1 = new Plato();
        platoBD1.setId(platoId1);
        platoBD1.setNombre(randomNombre());
        platoBD1.setPrecio(randomPrecio());
        platoBD1.setIdRestaurante(restauranteId);

        Plato platoBD2 = new Plato();
        platoBD2.setId(platoId2);
        platoBD2.setNombre(randomNombre());
        platoBD2.setPrecio(randomPrecio());
        platoBD2.setIdRestaurante(restauranteId);

        when(platoPersistencePort.obtenerPlatoPorId(platoId1)).thenReturn(Optional.of(platoBD1));
        when(platoPersistencePort.obtenerPlatoPorId(platoId2)).thenReturn(Optional.of(platoBD2));

        // Mock: guardar pedido
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedidoGuardado = realizarPedidoUseCase.ejecutar(pedido);

        assertEquals("PENDIENTE", pedidoGuardado.getEstado());
        assertEquals(2, pedidoGuardado.getPlatos().size());
        assertNotNull(pedidoGuardado.getPlatos().get(0).getNombre());
        assertTrue(pedidoGuardado.getPlatos().get(0).getPrecio() > 0);
    }

    @Test
    void crearPedidoPlatoNoExisteDebeFallar() {
        long clienteId = randomId();
        long restauranteId = randomId();
        long platoId = randomId();

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);

        PlatoPedido plato = new PlatoPedido();
        plato.setPlatoId(platoId);
        plato.setCantidad(1);

        pedido.setPlatos(List.of(plato));

        when(pedidoPersistencePort.tienePedidoEnProceso(clienteId)).thenReturn(false);
        when(platoPersistencePort.obtenerPlatoPorId(platoId)).thenReturn(Optional.empty());

        PedidoException ex = assertThrows(PedidoException.class,
                () -> realizarPedidoUseCase.ejecutar(pedido));

        assertEquals("El plato con ID " + platoId + " no existe", ex.getMessage());
    }

    @Test
    void crearPedidoPlatoNoPerteneceAlRestauranteDebeFallar() {
        long clienteId = randomId();
        long restauranteId = randomId();
        long platoId = randomId();
        long otroRestauranteId = restauranteId + 1;

        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);

        PlatoPedido plato = new PlatoPedido();
        plato.setPlatoId(platoId);
        plato.setCantidad(1);

        pedido.setPlatos(List.of(plato));

        when(pedidoPersistencePort.tienePedidoEnProceso(clienteId)).thenReturn(false);

        Plato platoBD = new Plato();
        platoBD.setId(platoId);
        platoBD.setNombre(randomNombre());
        platoBD.setPrecio(randomPrecio());
        platoBD.setIdRestaurante(otroRestauranteId); // plato de otro restaurante

        when(platoPersistencePort.obtenerPlatoPorId(platoId)).thenReturn(Optional.of(platoBD));

        PedidoException ex = assertThrows(PedidoException.class,
                () -> realizarPedidoUseCase.ejecutar(pedido));

        assertEquals("Todos los platos deben pertenecer al mismo restaurante", ex.getMessage());
    }
}