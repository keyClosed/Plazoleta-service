package com.plaza.plazoleta_service.infrastructure.configuration;

import com.plaza.plazoleta_service.application.mapper.PedidoMapper;
import com.plaza.plazoleta_service.application.mapper.RestauranteMapper;
import com.plaza.plazoleta_service.domain.spi.IPedidoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.IPlatoPersistencePort;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import com.plaza.plazoleta_service.domain.spi.ITrazabilidadClientPort;
import com.plaza.plazoleta_service.domain.spi.MensajeriaPersistencePort;
import com.plaza.plazoleta_service.domain.usecase.*;
import com.plaza.plazoleta_service.infrastructure.client.UsuariosClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig{

    @Bean
    public RestauranteMapper restauranteMapper() {
        return new RestauranteMapper();
    }

    @Bean
    public CrearPlatoUseCase crearPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new CrearPlatoUseCase(platoPersistencePort);
    }

    @Bean
    public ModificarPlatoUseCase modificarPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new ModificarPlatoUseCase(platoPersistencePort);
    }

    @Bean
    public CambiarEstadoPlatoUseCase cambiarEstadoPlatoUseCase(
            IPlatoPersistencePort platoPersistencePort
    ) {
        return new CambiarEstadoPlatoUseCase(platoPersistencePort);
    }

    @Bean
    public ListarRestaurantesUseCase listarRestaurantesUseCase(
            IRestaurantePersistencePort restaurantePersistencePort
    ) {
        return new ListarRestaurantesUseCase(restaurantePersistencePort);
    }

    @Bean
    public ListarPlatosUseCase listarPlatosUseCase(IPlatoPersistencePort platoPersistencePort) {
        return new ListarPlatosUseCase(platoPersistencePort);
    }

    // ✅ ACTUALIZADO: Inyecta ITrazabilidadClientPort
    @Bean
    public RealizarPedidoUseCase realizarPedidoUseCase(
            IPedidoPersistencePort pedidoPersistencePort,
            IPlatoPersistencePort platoPersistencePort,
            ITrazabilidadClientPort trazabilidadClientPort
    ) {
        return new RealizarPedidoUseCase(pedidoPersistencePort, platoPersistencePort, trazabilidadClientPort);
    }

    @Bean
    public ListarPedidosUseCase listarPedidosUseCase(IPedidoPersistencePort pedidoPersistencePort) {
        return new ListarPedidosUseCase(pedidoPersistencePort);
    }

    // ✅ ACTUALIZADO: Inyecta ITrazabilidadClientPort
    @Bean
    public AsignarsePedidoUseCase asignarsePedidoUseCase(
            IPedidoPersistencePort pedidoPersistencePort,
            ITrazabilidadClientPort trazabilidadClientPort
    ) {
        return new AsignarsePedidoUseCase(pedidoPersistencePort, trazabilidadClientPort);
    }

    // ✅ ACTUALIZADO: Inyecta ITrazabilidadClientPort
    @Bean
    public PedidoListoUseCase pedidoListoUseCase(
            IPedidoPersistencePort pedidoPersistencePort,
            MensajeriaPersistencePort mensajeriaPort,
            ITrazabilidadClientPort trazabilidadClientPort
    ) {
        return new PedidoListoUseCase(pedidoPersistencePort, mensajeriaPort, trazabilidadClientPort);
    }

    // ✅ ACTUALIZADO: Inyecta ITrazabilidadClientPort
    @Bean
    public EntregarPedidoUseCase entregarPedidoUseCase(
            IPedidoPersistencePort pedidoPersistencePort,
            ITrazabilidadClientPort trazabilidadClientPort
    ) {
        return new EntregarPedidoUseCase(pedidoPersistencePort, trazabilidadClientPort);
    }

    // ✅ ACTUALIZADO: Inyecta ITrazabilidadClientPort
    @Bean
    public CancelarPedidoUseCase cancelarPedidoUseCase(
            IPedidoPersistencePort pedidoPersistencePort,
            ITrazabilidadClientPort trazabilidadClientPort
    ) {
        return new CancelarPedidoUseCase(pedidoPersistencePort, trazabilidadClientPort);
    }
}