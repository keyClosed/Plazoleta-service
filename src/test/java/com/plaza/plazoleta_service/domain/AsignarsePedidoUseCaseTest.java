package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.AsignarsePedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AsignarsePedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @InjectMocks
    private AsignarsePedidoUseCase asignarsePedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ejecutar_debeLanzarExcepcionSiPedidoNoExiste() {
        when(pedidoPersistencePort.obtenerPedidoPorId(anyLong()))
                .thenReturn(null);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> asignarsePedidoUseCase.ejecutar(
                        ThreadLocalRandom.current().nextLong(1, 1000),
                        ThreadLocalRandom.current().nextLong(1, 1000)
                )
        );

        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(pedidoPersistencePort, never()).guardarPedido(any());
    }

    @Test
    void ejecutar_debeAsignarEmpleadoYActualizarEstado() {
        long idPedido = ThreadLocalRandom.current().nextLong(1, 1000);
        long idEmpleado = ThreadLocalRandom.current().nextLong(1, 1000);
        String estadoInicial = "PENDIENTE";

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setEstado(estadoInicial);

        when(pedidoPersistencePort.obtenerPedidoPorId(idPedido))
                .thenReturn(pedido);

        when(pedidoPersistencePort.guardarPedido(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = asignarsePedidoUseCase.ejecutar(idPedido, idEmpleado);

        assertNotNull(resultado);
        assertEquals(idEmpleado, resultado.getEmpleadoAsignadoId());
        assertEquals("EN_PREPARACION", resultado.getEstado());

        verify(pedidoPersistencePort).guardarPedido(pedido);
    }
}