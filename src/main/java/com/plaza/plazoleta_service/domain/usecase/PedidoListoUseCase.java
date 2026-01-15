package com.plaza.plazoleta_service.domain.usecase;

import com.plaza.plazoleta_service.domain.api.IPedidoListoService;
import com.plaza.plazoleta_service.domain.exception.PedidoException;
import com.plaza.plazoleta_service.domain.model.Pedido;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.MensajeriaPersistencePort;
import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import com.plaza.plazoleta_service.application.dto.request.MensajeSmsRequest;
import com.plaza.plazoleta_service.application.dto.response.MensajeSmsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoListoUseCase implements IPedidoListoService {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final MensajeriaPersistencePort mensajeriaPort;
    private final ITrazabilidadClientPort trazabilidadClientPort;

    public PedidoListoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                              MensajeriaPersistencePort mensajeriaPort,
                              ITrazabilidadClientPort trazabilidadClientPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.mensajeriaPort = mensajeriaPort;
        this.trazabilidadClientPort = trazabilidadClientPort;
    }

    @Override
    @Transactional
    public Pedido marcarPedidoComoListo(Long idPedido) {

        Pedido pedido = pedidoPersistencePort.obtenerPedidoPorId(idPedido);
        if (pedido == null) {
            throw new PedidoException("Pedido no encontrado");
        }
        pedido.setEstado("LISTO");

        Pedido pedidoGuardado = pedidoPersistencePort.guardarPedido(pedido);

        try {
            trazabilidadClientPort.registrarCambioEstado(
                    pedidoGuardado.getId(),
                    pedidoGuardado.getClienteId(),
                    "LISTO",
                    "Pedido listo para recoger. SMS enviado al cliente"
            );
        } catch (Exception e) {
            System.err.println("No se pudo registrar trazabilidad: " + e.getMessage());
        }

        if (pedidoGuardado.getClienteTelefono() != null && pedidoGuardado.getPinSeguridad() != null) {
            String mensajeSms = String.format(
                    "Tu pedido #%d está listo para recoger. PIN de seguridad: %d",
                    pedidoGuardado.getId(),
                    pedidoGuardado.getPinSeguridad()
            );

            MensajeSmsRequest smsRequest = new MensajeSmsRequest(
                    pedidoGuardado.getClienteTelefono(),
                    mensajeSms,
                    pedidoGuardado.getPinSeguridad()
            );

            MensajeSmsResponse response = mensajeriaPort.enviarSms(smsRequest);
            System.out.println("Respuesta del servicio de mensajería: " + response.getEstado());
        }

        return pedidoGuardado;
    }
}