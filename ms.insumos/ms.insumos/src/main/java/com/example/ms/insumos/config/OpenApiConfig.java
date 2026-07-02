package com.example.ms.insumos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentar los endpoints
 * del microservicio de insumos.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura la metadata de la API (título, descripción y versión)
     * que se mostrará en la interfaz de Swagger.
     *
     * @return objeto OpenAPI con la información de la API
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-insumos")
                                .description("Microservicio de gestión de insumos")
                                .version("1.0.0")
                );
    }
}