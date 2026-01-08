package com.plaza.plazoleta_service.infrastructure.out.jpa.adapter;

import com.plaza.plazoleta_service.domain.model.Propietario;
import com.plaza.plazoleta_service.domain.spi.IPropietarioClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

            // 🔐 Obtener JWT del contexto de seguridad
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String token = (String) authentication.getCredentials();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            Map<String, Object> body = response.getBody();

            if (body == null) {
                throw new RuntimeException("Usuario con ID " + id + " no encontrado");
            }

            String rol = (String) body.get("rol");
            String nombre = (String) body.get("nombre");

            return new Propietario(String.valueOf(id), nombre, rol);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error al obtener propietario con ID " + id + ": " + e.getMessage(), e
            );
        }
    }
}