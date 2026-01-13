package com.plaza.plazoleta_service.domain;

import com.plaza.plazoleta_service.application.dto.request.MensajeSmsRequest;
import com.plaza.plazoleta_service.application.dto.response.MensajeSmsResponse;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.MensajeriaPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.PedidoListoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoListoUseCaseTest {

    @Mock
    private IPedidoPersistencePort pedidoPersistencePort;

    @Mock
    private MensajeriaPersistencePort mensajeriaPort;

    @InjectMocks
    private PedidoListoUseCase pedidoListoUseCase;

    private Pedido pedido;
    private Long pedidoId;
    private Long clienteId;
    private Long restauranteId;
    private Integer pinSeguridad;
    private String telefono;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        pedidoId = random.nextLong(1, 1000);
        clienteId = random.nextLong(1, 1000);
        restauranteId = random.nextLong(1, 100);
        pinSeguridad = 1000 + random.nextInt(9000);
        telefono = generarTelefonoAleatorio();

        pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado("EN_PREPARACION");
        pedido.setClienteTelefono(telefono);
        pedido.setPinSeguridad(pinSeguridad);
    }

    private String generarTelefonoAleatorio() {
        long numero = 3000000000L + random.nextInt(100000000);
        return "+57" + numero;
    }

    @Test
    void marcarPedidoComoListo_DeberiaActualizarEstadoYEnviarSms() {
        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);
        MensajeSmsResponse smsResponse = new MensajeSmsResponse("ENVIADO", "Mensaje enviado");
        when(mensajeriaPort.enviarSms(any(MensajeSmsRequest.class))).thenReturn(smsResponse);
        Pedido resultado = pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
        assertEquals("LISTO", resultado.getEstado());
        verify(pedidoPersistencePort).guardarPedido(any(Pedido.class));
        verify(mensajeriaPort).enviarSms(any(MensajeSmsRequest.class));
    }

    @Test
    void marcarPedidoComoListo_DeberiaConstruirMensajeConPinCorrectamente() {

        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);
        MensajeSmsResponse smsResponse = new MensajeSmsResponse("ENVIADO", "Mensaje enviado");
        when(mensajeriaPort.enviarSms(any(MensajeSmsRequest.class))).thenReturn(smsResponse);
        ArgumentCaptor<MensajeSmsRequest> smsCaptor = ArgumentCaptor.forClass(MensajeSmsRequest.class);
        pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
        verify(mensajeriaPort).enviarSms(smsCaptor.capture());
        MensajeSmsRequest smsRequest = smsCaptor.getValue();
        assertEquals(telefono, smsRequest.getTelefono());
        assertTrue(smsRequest.getMensaje().contains("Tu pedido #" + pedidoId + " está listo"));
        assertTrue(smsRequest.getMensaje().contains("PIN de seguridad: " + pinSeguridad));
        assertEquals(pinSeguridad, smsRequest.getPin());
    }

    @Test
    void marcarPedidoComoListo_DeberiaLanzarExcepcionCuandoPedidoNoExiste() {

        Long pedidoInexistenteId = random.nextLong(10000, 20000);
        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoInexistenteId)).thenReturn(null);
        PedidoException exception = assertThrows(PedidoException.class, () -> {
            pedidoListoUseCase.marcarPedidoComoListo(pedidoInexistenteId);
        });
        assertEquals("Pedido no encontrado", exception.getMessage());
        verify(pedidoPersistencePort, never()).guardarPedido(any());
        verify(mensajeriaPort, never()).enviarSms(any());
    }

    @Test
    void marcarPedidoComoListo_NoDeberiaEnviarSmsSiTelefonoEsNull() {
        pedido.setClienteTelefono(null);
        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);
        Pedido resultado = pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
        assertEquals("LISTO", resultado.getEstado());
        verify(pedidoPersistencePort).guardarPedido(any(Pedido.class));
        verify(mensajeriaPort, never()).enviarSms(any());
    }

    @Test
    void marcarPedidoComoListo_NoDeberiaEnviarSmsSiPinEsNull() {
        pedido.setPinSeguridad(null);
        when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
        when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);
        Pedido resultado = pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
        assertEquals("LISTO", resultado.getEstado());
        verify(pedidoPersistencePort).guardarPedido(any(Pedido.class));
        verify(mensajeriaPort, never()).enviarSms(any());
    }

    @Test
    void marcarPedidoComoListo_DeberiaFuncionarConDiferentesPines() {
        Integer pin1 = 1000 + random.nextInt(9000);
        Integer pin2 = 1000 + random.nextInt(9000);
        Integer pin3 = 1000 + random.nextInt(9000);

        for (Integer pin : new Integer[]{pin1, pin2, pin3}) {
            pedido.setPinSeguridad(pin);
            when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
            when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);

            MensajeSmsResponse smsResponse = new MensajeSmsResponse("ENVIADO", "Mensaje enviado");
            when(mensajeriaPort.enviarSms(any(MensajeSmsRequest.class))).thenReturn(smsResponse);
            ArgumentCaptor<MensajeSmsRequest> captor = ArgumentCaptor.forClass(MensajeSmsRequest.class);
            pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
            verify(mensajeriaPort, atLeastOnce()).enviarSms(captor.capture());
            MensajeSmsRequest request = captor.getValue();
            assertEquals(pin, request.getPin());
            assertTrue(request.getMensaje().contains("PIN de seguridad: " + pin));
        }
    }

    @Test
    void marcarPedidoComoListo_DeberiaFuncionarConDiferentesTelefonos() {

        String[] telefonos = {
                generarTelefonoAleatorio(),
                generarTelefonoAleatorio(),
                generarTelefonoAleatorio()
        };

        for (String tel : telefonos) {
            pedido.setClienteTelefono(tel);
            when(pedidoPersistencePort.obtenerPedidoPorId(pedidoId)).thenReturn(pedido);
            when(pedidoPersistencePort.guardarPedido(any(Pedido.class))).thenReturn(pedido);
            MensajeSmsResponse smsResponse = new MensajeSmsResponse("ENVIADO", "Mensaje enviado");
            when(mensajeriaPort.enviarSms(any(MensajeSmsRequest.class))).thenReturn(smsResponse);
            ArgumentCaptor<MensajeSmsRequest> captor = ArgumentCaptor.forClass(MensajeSmsRequest.class);
            pedidoListoUseCase.marcarPedidoComoListo(pedidoId);
            verify(mensajeriaPort, atLeastOnce()).enviarSms(captor.capture());
            assertEquals(tel, captor.getValue().getTelefono());
        }
    }
}