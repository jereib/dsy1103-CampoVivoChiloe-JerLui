package com.example.ms_liquidaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Configuración de Swagger/OpenAPI para el microservicio.
 * Define la información descriptiva que aparece en la interfaz de Swagger UI.
 */
public class OpenApiConfig {

    /**
     * Configura y expone el bean de OpenAPI para documentar los endpoints
     * del microservicio con Swagger.
     *
     * @return objeto OpenAPI con la información de la API.
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-liquidaciones")
                                .description("Microservicio de gestión de liquidaciones")
                                .version("1.0.0")
                );
    }
}
