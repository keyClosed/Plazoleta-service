package com.plaza.plazoleta_service.application.handler.impl;

import com.plaza.plazoleta_service.application.dto.request.CrearRestauranteRequest;
import com.plaza.plazoleta_service.application.dto.response.RestauranteResponse;
import com.plaza.plazoleta_service.application.handler.CrearRestauranteHandler;
import com.plaza.plazoleta_service.domain.model.Propietario;
import com.plaza.plazoleta_service.domain.model.Restaurante;
import com.plaza.plazoleta_service.domain.spi.IPropietarioClientPort;
import com.plaza.plazoleta_service.domain.spi.IRestaurantePersistencePort;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CrearRestauranteHandlerTest {

    private final IRestaurantePersistencePort restaurantePersistencePort = mock(IRestaurantePersistencePort.class);
    private final IPropietarioClientPort propietarioClientPort = mock(IPropietarioClientPort.class);
    private final CrearRestauranteHandler handler = new CrearRestauranteHandlerImpl(
            restaurantePersistencePort,
            propietarioClientPort
    );

    private final Random random = new Random();

    @Test
    void crearRestauranteConDatosAleatorios() {
        // Generar datos aleatorios simples
        String nombre = "Restaurante-" + UUID.randomUUID().toString().substring(0, 5);
        String nit = String.valueOf(100000000 + random.nextInt(900000000)); // 9 dígitos
        String direccion = "Calle " + (random.nextInt(999) + 1);
        String telefono = "+" + (100000000000L + (long)(random.nextDouble() * 899999999999L)); // hasta 13 caracteres
        String urlLogo = "https://example.com/logo" + random.nextInt(1000) + ".png";
        Long idPropietario = 1L + random.nextInt(100);

        CrearRestauranteRequest request = new CrearRestauranteRequest();
        request.setNombre(nombre);
        request.setNit(nit);
        request.setDireccion(direccion);
        request.setTelefono(telefono);
        request.setUrlLogo(urlLogo);
        request.setIdPropietario(idPropietario);

        // Mockear propietario
        Propietario propietario = new Propietario(String.valueOf(idPropietario), "Juan", "PROPIETARIO");
        when(propietarioClientPort.obtenerPropietarioPorId(idPropietario)).thenReturn(propietario);

        // Mockear persistencia
        Restaurante restauranteGuardado = new Restaurante(nombre, nit, direccion, telefono, urlLogo, idPropietario);
        restauranteGuardado.setId(100L + random.nextInt(900)); // id aleatorio
        when(restaurantePersistencePort.guardarRestaurante(any(Restaurante.class)))
                .thenReturn(restauranteGuardado);

        // Ejecutar handler
        RestauranteResponse response = handler.crearRestaurante(request);

        // Validaciones
        assertNotNull(response);
        assertEquals(restauranteGuardado.getId(), response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(idPropietario, response.getIdPropietario());

        verify(propietarioClientPort).obtenerPropietarioPorId(idPropietario);
        verify(restaurantePersistencePort).guardarRestaurante(any(Restaurante.class));
    }
}
