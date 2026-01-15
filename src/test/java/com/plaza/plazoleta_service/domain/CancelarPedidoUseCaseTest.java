package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.model.PlatoPedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.CancelarPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelarPedidoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @InjectMocks
    private CancelarPedidoUseCase cancelarPedidoUseCase;

    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    private Pedido generarPedidoAleatorio(String estado) {
        Pedido pedido = new Pedido();
        pedido.setId(random.nextLong(1, 10000));
        pedido.setEstado(estado);
        pedido.setClienteId(random.nextLong(1, 1000));
        pedido.setRestauranteId(random.nextLong(1, 100));
        pedido.setEmpleadoAsignadoId(random.nextLong(1, 50));
        pedido.setClienteTelefono(generarTelefonoAleatorio());
        pedido.setPinSeguridad(random.nextInt(1000, 9999));
        pedido.setPlatos(generarPlatosAleatorios());
        return pedido;
    }

    private String generarTelefonoAleatorio() {
        return String.format("+57%d%d",
                random.nextInt(300, 321),
                random.nextInt(1000000, 9999999)
        );
    }

    private List<PlatoPedido> generarPlatosAleatorios() {
        List<PlatoPedido> platos = new ArrayList<>();
        int cantidadPlatos = random.nextInt(1, 5);

        for (int i = 0; i < cantidadPlatos; i++) {
            PlatoPedido plato = new PlatoPedido();
            plato.setPlatoId(random.nextLong(1, 100));
            plato.setCantidad(random.nextInt(1, 5));
            platos.add(plato);
        }

        return platos;
    }

    @Test
    void deberiaCancelarPedidoPendienteExitosamente() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedidoPendiente = generarPedidoAleatorio("PENDIENTE");
        pedidoPendiente.setId(pedidoId);

        Pedido pedidoCancelado = new Pedido();
        pedidoCancelado.setId(pedidoId);
        pedidoCancelado.setEstado("CANCELADO");
        pedidoCancelado.setClienteId(pedidoPendiente.getClienteId());
        pedidoCancelado.setRestauranteId(pedidoPendiente.getRestauranteId());
        pedidoCancelado.setClienteTelefono(pedidoPendiente.getClienteTelefono());
        pedidoCancelado.setPinSeguridad(pedidoPendiente.getPinSeguridad());

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoPendiente);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedidoCancelado);

        Pedido resultado = cancelarPedidoUseCase.cancelarPedido(pedidoId);

        assertNotNull(resultado);
        assertEquals("CANCELADO", resultado.getEstado());
        assertEquals(pedidoId, resultado.getId());
        verify(pedidoPersistencePort, times(1)).obtenerPedidoPorId(pedidoId);
        verify(pedidoPersistencePort, times(1)).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoNoExiste() {

        Long pedidoIdInexistente = random.nextLong(10000, 99999);
        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoIdInexistente)).thenReturn(null);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> cancelarPedidoUseCase.cancelarPedido(pedidoIdInexistente)
        );

        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(pedidoPersistencePort, times(1)).obtenerPedidoPorId(pedidoIdInexistente);
        verify(pedidoPersistencePort, never()).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoEstaEnPreparacion() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedidoEnPreparacion = generarPedidoAleatorio("EN_PREPARACION");
        pedidoEnPreparacion.setId(pedidoId);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoEnPreparacion);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> cancelarPedidoUseCase.cancelarPedido(pedidoId)
        );

        assertEquals(
                "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse",
                exception.getMessage()
        );
        verify(pedidoPersistencePort, times(1)).obtenerPedidoPorId(pedidoId);
        verify(pedidoPersistencePort, never()).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoEstaListo() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedidoListo = generarPedidoAleatorio("LISTO");
        pedidoListo.setId(pedidoId);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoListo);

        PedidoException exception = assertThrows(
                PedidoException.class,
                () -> cancelarPedidoUseCase.cancelarPedido(pedidoId)
        );

        assertEquals(
                "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse",
                exception.getMessage()
        );
        verify(pedidoPersistencePort, never()).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoEstaEntregado() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedidoEntregado = generarPedidoAleatorio("ENTREGADO");
        pedidoEntregado.setId(pedidoId);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoEntregado);

        assertThrows(
                PedidoException.class,
                () -> cancelarPedidoUseCase.cancelarPedido(pedidoId)
        );
        verify(pedidoPersistencePort, never()).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoPedidoYaEstaCancelado() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedidoCancelado = generarPedidoAleatorio("CANCELADO");
        pedidoCancelado.setId(pedidoId);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoCancelado);

        assertThrows(
                PedidoException.class,
                () -> cancelarPedidoUseCase.cancelarPedido(pedidoId)
        );
        verify(pedidoPersistencePort, never()).guardarPedido(any(Pedido.class));
    }

    @Test
    void deberiaMantenerTodosLosDatosDelPedidoAlCancelar() {
        Long pedidoId = random.nextLong(1, 10000);
        Long clienteId = random.nextLong(1, 1000);
        Long restauranteId = random.nextLong(1, 100);
        Long empleadoId = random.nextLong(1, 50);
        String telefono = generarTelefonoAleatorio();
        Integer pin = random.nextInt(1000, 9999);
        List<PlatoPedido> platos = generarPlatosAleatorios();

        Pedido pedidoOriginal = new Pedido();
        pedidoOriginal.setId(pedidoId);
        pedidoOriginal.setEstado("PENDIENTE");
        pedidoOriginal.setClienteId(clienteId);
        pedidoOriginal.setRestauranteId(restauranteId);
        pedidoOriginal.setEmpleadoAsignadoId(empleadoId);
        pedidoOriginal.setClienteTelefono(telefono);
        pedidoOriginal.setPinSeguridad(pin);
        pedidoOriginal.setPlatos(platos);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedidoOriginal);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Pedido resultado = cancelarPedidoUseCase.cancelarPedido(pedidoId);

        assertEquals(pedidoId, resultado.getId());
        assertEquals(clienteId, resultado.getClienteId());
        assertEquals(restauranteId, resultado.getRestauranteId());
        assertEquals(empleadoId, resultado.getEmpleadoAsignadoId());
        assertEquals(telefono, resultado.getClienteTelefono());
        assertEquals(pin, resultado.getPinSeguridad());
        assertEquals(platos, resultado.getPlatos());
        assertEquals("CANCELADO", resultado.getEstado());
    }

    @Test
    void deberiaCancelarPedidoConMultiplesPlatos() {
        Long pedidoId = random.nextLong(1, 10000);
        Pedido pedido = generarPedidoAleatorio("PENDIENTE");
        pedido.setId(pedidoId);

        List<PlatoPedido> platosMultiples = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PlatoPedido plato = new PlatoPedido();
            plato.setPlatoId(random.nextLong(1, 100));
            plato.setCantidad(random.nextInt(1, 5));
            platosMultiples.add(plato);
        }
        pedido.setPlatos(platosMultiples);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Pedido resultado = cancelarPedidoUseCase.cancelarPedido(pedidoId);

        assertEquals("CANCELADO", resultado.getEstado());
        assertEquals(5, resultado.getPlatos().size());
        assertNotNull(resultado.getPlatos());
    }

    @Test
    void deberiaCancelarPedidoConPinValido() {
        Long pedidoId = random.nextLong(1, 10000);
        Integer pinEsperado = random.nextInt(1000, 9999);

        Pedido pedido = generarPedidoAleatorio("PENDIENTE");
        pedido.setId(pedidoId);
        pedido.setPinSeguridad(pinEsperado);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Pedido resultado = cancelarPedidoUseCase.cancelarPedido(pedidoId);

        assertEquals("CANCELADO", resultado.getEstado());
        assertEquals(pinEsperado, resultado.getPinSeguridad());
    }

    @Test
    void deberiaCancelarPedidoConTelefonoValido() {
        Long pedidoId = random.nextLong(1, 10000);
        String telefonoEsperado = generarTelefonoAleatorio();

        Pedido pedido = generarPedidoAleatorio("PENDIENTE");
        pedido.setId(pedidoId);
        pedido.setClienteTelefono(telefonoEsperado);

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenAnswer(
                invocation -> invocation.getArgument(0)
        );

        Pedido resultado = cancelarPedidoUseCase.cancelarPedido(pedidoId);

        assertEquals("CANCELADO", resultado.getEstado());
        assertEquals(telefonoEsperado, resultado.getClienteTelefono());
    }
}