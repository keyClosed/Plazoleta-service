package com.plaza.plazoleta_service.application.handler.impl;


import com.plaza.plazoleta_service.application.dto.response.PedidoListoResponse;
import com.plaza.plazoleta_service.application.mapper.PedidoListoMapper;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.usecase.PedidoListoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoListoHandlerImplTest {

    @Mock
    private PedidoListoUseCase pedidoListoUseCase;

    @Mock
    private PedidoListoMapper pedidoListoMapper;

    @InjectMocks
    private PedidoListoHandlerImpl pedidoListoHandler;

    private Pedido pedido;
    private PedidoListoResponse pedidoListoResponse;
    private Random random;
    private Long pedidoId;
    private Long clienteId;
    private Long restauranteId;
    private Integer pinSeguridad;
    private String telefono;
    private String estado;
    private String mensajeResponse;

    @BeforeEach
    void setUp() {
        random = new Random();

        pedidoId = random.nextLong(1, 10000);
        clienteId = random.nextLong(1, 5000);
        restauranteId = random.nextLong(1, 500);
        pinSeguridad = 1000 + random.nextInt(9000);
        telefono = generarTelefonoAleatorio();
        estado = "LISTO";
        mensajeResponse = generarMensajeAleatorio();

        pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado(estado);
        pedido.setClienteTelefono(telefono);
        pedido.setPinSeguridad(pinSeguridad);

        pedidoListoResponse = new PedidoListoResponse();
        pedidoListoResponse.setId(pedidoId);
        pedidoListoResponse.setEstado(estado);
        pedidoListoResponse.setMensaje(mensajeResponse);
    }

    private String generarTelefonoAleatorio() {
        long numero = 3000000000L + random.nextInt(100000000);
        return "+57" + numero;
    }

    private String generarMensajeAleatorio() {
        String[] mensajes = {
                "Mensaje SMS enviado al cliente",
                "SMS enviado correctamente",
                "Notificación enviada con éxito",
                "Cliente notificado por SMS",
                "SMS de pedido listo enviado"
        };
        return mensajes[random.nextInt(mensajes.length)];
    }

    @Test
    void ejecutar_DeberiaMarcarPedidoComoListoYRetornarResponse() {

        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoId)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(pedidoListoResponse);
        PedidoListoResponse resultado = pedidoListoHandler.ejecutar(pedidoId);
        assertNotNull(resultado);
        assertEquals(pedidoId, resultado.getId());
        assertEquals(estado, resultado.getEstado());
        assertEquals(mensajeResponse, resultado.getMensaje());
        verify(pedidoListoUseCase).marcarPedidoComoListo(pedidoId);
        verify(pedidoListoMapper).toResponse(pedido, "Mensaje SMS enviado al cliente");
    }

    @Test
    void ejecutar_DeberiaLlamarAlUseCaseConElIdCorrecto() {
        Long pedidoIdAleatorio = random.nextLong(1, 50000);
        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoIdAleatorio)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(pedidoListoResponse);
        pedidoListoHandler.ejecutar(pedidoIdAleatorio);
        verify(pedidoListoUseCase).marcarPedidoComoListo(pedidoIdAleatorio);
    }

    @Test
    void ejecutar_DeberiaUsarElMapperCorrectamente() {

        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoId)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(pedidoListoResponse);
        pedidoListoHandler.ejecutar(pedidoId);
        verify(pedidoListoMapper).toResponse(pedido, "Mensaje SMS enviado al cliente");
    }

    @Test
    void ejecutar_DeberiaRetornarResponseConDatosCorrectos() {
        Long idAleatorio = random.nextLong(1, 100000);
        String estadoAleatorio = "LISTO";
        String mensajeAleatorio = generarMensajeAleatorio();
        PedidoListoResponse expectedResponse = new PedidoListoResponse();
        expectedResponse.setId(idAleatorio);
        expectedResponse.setEstado(estadoAleatorio);
        expectedResponse.setMensaje(mensajeAleatorio);
        when(pedidoListoUseCase.marcarPedidoComoListo(idAleatorio)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(expectedResponse);
        PedidoListoResponse resultado = pedidoListoHandler.ejecutar(idAleatorio);
        assertEquals(idAleatorio, resultado.getId());
        assertEquals(estadoAleatorio, resultado.getEstado());
        assertEquals(mensajeAleatorio, resultado.getMensaje());
    }

    @Test
    void ejecutar_DeberiaInteractuarCorrectamenteConDependencias() {
        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoId)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(pedidoListoResponse);
        pedidoListoHandler.ejecutar(pedidoId);
        verify(pedidoListoUseCase, times(1)).marcarPedidoComoListo(pedidoId);
        verify(pedidoListoMapper, times(1)).toResponse(pedido, "Mensaje SMS enviado al cliente");
        verifyNoMoreInteractions(pedidoListoUseCase, pedidoListoMapper);
    }

    @Test
    void ejecutar_DeberiaFuncionarConMultiplesPedidosAleatorios() {
        for (int i = 0; i < 5; i++) {
            Long idRandom = random.nextLong(1, 100000);
            Pedido pedidoRandom = crearPedidoAleatorio(idRandom);
            PedidoListoResponse responseRandom = crearResponseAleatorio(idRandom);
            when(pedidoListoUseCase.marcarPedidoComoListo(idRandom)).thenReturn(pedidoRandom);
            when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(responseRandom);
            PedidoListoResponse resultado = pedidoListoHandler.ejecutar(idRandom);
            assertNotNull(resultado);
            assertEquals(idRandom, resultado.getId());
            verify(pedidoListoUseCase).marcarPedidoComoListo(idRandom);
        }
    }

    @Test
    void ejecutar_DeberiaLanzarExcepcionCuandoPedidoNoExiste() {

        Long pedidoInexistenteId = random.nextLong(100000, 200000);
        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoInexistenteId))
                .thenThrow(new PedidoException("Pedido no encontrado"));
        assertThrows(PedidoException.class, () -> {
            pedidoListoHandler.ejecutar(pedidoInexistenteId);
        });
        verify(pedidoListoUseCase).marcarPedidoComoListo(pedidoInexistenteId);
        verify(pedidoListoMapper, never()).toResponse(any(), anyString());
    }

    @Test
    void ejecutar_DeberiaRetornarResponseConTodosLosCamposPoblados() {
        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoId)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(any(Pedido.class), anyString())).thenReturn(pedidoListoResponse);
        PedidoListoResponse resultado = pedidoListoHandler.ejecutar(pedidoId);
        assertNotNull(resultado.getId(), "El ID no debe ser null");
        assertNotNull(resultado.getEstado(), "El estado no debe ser null");
        assertNotNull(resultado.getMensaje(), "El mensaje no debe ser null");
    }

    @Test
    void ejecutar_DeberiaUtilizarMensajeFijoPorDefecto() {
        String mensajeEsperado = "Mensaje SMS enviado al cliente";
        when(pedidoListoUseCase.marcarPedidoComoListo(pedidoId)).thenReturn(pedido);
        when(pedidoListoMapper.toResponse(pedido, mensajeEsperado)).thenReturn(pedidoListoResponse);
        pedidoListoHandler.ejecutar(pedidoId);
        verify(pedidoListoMapper).toResponse(pedido, mensajeEsperado);
    }

    private Pedido crearPedidoAleatorio(Long id) {
        Pedido p = new Pedido();
        p.setId(id);
        p.setClienteId(random.nextLong(1, 10000));
        p.setRestauranteId(random.nextLong(1, 1000));
        p.setEstado("LISTO");
        p.setClienteTelefono(generarTelefonoAleatorio());
        p.setPinSeguridad(1000 + random.nextInt(9000));
        return p;
    }

    private PedidoListoResponse crearResponseAleatorio(Long id) {
        PedidoListoResponse r = new PedidoListoResponse();
        r.setId(id);
        r.setEstado("LISTO");
        r.setMensaje(generarMensajeAleatorio());
        return r;
    }
}