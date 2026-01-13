package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.EntregarPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntregarPedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @InjectMocks
    private EntregarPedidoUseCase entregarPedidoUseCase;

    private Pedido pedido;
    private Long pedidoId;
    private Integer pinCorrecto;

    @BeforeEach
    void setUp() {
        Random random = new Random();

        pedidoId = random.nextLong(1000);
        pinCorrecto = random.nextInt(9000) + 1000;

        pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setEstado("LISTO");
        pedido.setPinSeguridad(pinCorrecto);
    }

    @Test
    void debeEntregarPedidoCuandoEstadoEsListoYPinCorrecto() {

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId))
                .thenReturn(pedido);

        when(pedidoPersistencePort.guardarPedido(any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido resultado = entregarPedidoUseCase.entregarPedido(pedidoId, pinCorrecto);

        assertNotNull(resultado);
        assertEquals("ENTREGADO", resultado.getEstado());

        verify(pedidoPersistencePort).obtenerPedidoPorId(pedidoId);
        verify(pedidoPersistencePort).guardarPedido(pedido);
    }

    @Test
    void debeLanzarExcepcionSiPedidoNoExiste() {

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId))
                .thenReturn(null);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> entregarPedidoUseCase.entregarPedido(pedidoId, pinCorrecto)
        );

        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(pedidoPersistencePort, never()).guardarPedido(any());
    }

    @Test
    void debeLanzarExcepcionSiPedidoNoEstaEnEstadoListo() {

        pedido.setEstado("EN_PREPARACION");

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId))
                .thenReturn(pedido);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> entregarPedidoUseCase.entregarPedido(pedidoId, pinCorrecto)
        );

        assertEquals("Solo se pueden entregar pedidos en estado LISTO", exception.getMessage());
    }

    @Test
    void debeLanzarExcepcionSiPedidoNoTienePin() {

        pedido.setPinSeguridad(null);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId))
                .thenReturn(pedido);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> entregarPedidoUseCase.entregarPedido(pedidoId, pinCorrecto)
        );

        assertEquals("El pedido no tiene PIN de seguridad configurado", exception.getMessage());
    }

    @Test
    void debeLanzarExcepcionSiPinEsIncorrecto() {

        Integer pinIncorrecto = pinCorrecto + 1;

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId))
                .thenReturn(pedido);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> entregarPedidoUseCase.entregarPedido(pedidoId, pinIncorrecto)
        );

        assertEquals("PIN de seguridad incorrecto", exception.getMessage());
    }
}
