package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.model.Propietario;
import com.plaza.plazoleta_service.domain.spi.IPropietarioClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class PropietarioClientAdapter implements IPropietarioClientPort {

    private final RestTemplate restTemplate;
    private final String usuariosServiceUrl;

    public PropietarioClientAdapter(RestTemplate restTemplate,
                                    @Value("${usuarios.service.url}") String usuariosServiceUrl) {
        this.restTemplate = restTemplate;
        this.usuariosServiceUrl = usuariosServiceUrl;
    }

    @Override
    public Propietario obtenerPropietarioPorId(long id) {
        try {


            String url = usuariosServiceUrl + "/" + id;
            System.out.println(">> URL que se va a llamar: " + url);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                throw new RuntimeException("Usuario con ID " + id + " no encontrado en Usuarios Service");
            }


            String rol = (String) response.get("rol");
            String nombre = (String) response.get("nombre");

            return new Propietario(String.valueOf(id), nombre, rol);

        } catch (Exception e) {
            // Lanzamos la excepción para que Plazoleta Service lo reciba claramente
            throw new RuntimeException("Error al obtener propietario con ID " + id + ": " + e.getMessage(), e);
        }
    }
}