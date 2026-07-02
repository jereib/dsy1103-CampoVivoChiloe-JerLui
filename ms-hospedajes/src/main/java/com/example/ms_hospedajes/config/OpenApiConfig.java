package com.example.ms_hospedajes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI / Swagger para el microservicio de hospedajes.
 * Define la información básica de la API (título, descripción, versión).
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crea y configura el bean de OpenAPI con la metadata del microservicio.
     *
     * @return objeto OpenAPI con la configuración de la documentación.
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-hospedajes")
                                .description("Microservicio de gestión de los hospedajes")
                                .version("1.0.0")
                );
    }
}
