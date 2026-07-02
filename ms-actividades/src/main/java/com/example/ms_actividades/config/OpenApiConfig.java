package com.example.ms_actividades.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para el microservicio.
 * Define el título, la descripción y la versión de la API.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crea el bean de OpenAPI con los metadatos del microservicio.
     *
     * @return objeto OpenAPI con la información de la API.
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-actividades")
                                .description("Microservicio de gestión de actividades")
                                .version("1.0.0")
                );
    }
}
