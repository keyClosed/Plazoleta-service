package com.plaza.plazoleta_service.infrastructure.documentation;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio Plazoleta")
                        .description("API para crear y gestionar restaurantes")
                        .contact(new Contact()
                                .name("key ova ")
                                .email("keylyovadi@gmial.com")
                                .url("todavia no se despliega"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación del proyecto")
                        .url("https://github.com/keycloaset/microservicio-plazoleta")
                );
    }
}