package com.example.ms_huespedes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Configuración de Swagger/OpenAPI para el microservicio ms-huespedes.
 * Expone la documentación interactiva de la API.
 */
public class OpenApiConfig {

    @Bean
    /**
     * Configura la información general de la API de huéspedes.
     *
     * @return objeto OpenAPI con el título, descripción y versión.
     */
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-huespedes")
                                .description("Microservicio de gestión de los huespedes")
                                .version("1.0.0")
                );
    }
}