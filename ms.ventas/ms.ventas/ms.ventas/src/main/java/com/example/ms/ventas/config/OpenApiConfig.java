package com.example.ms.ventas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentar los endpoints
 * del microservicio de ventas.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crea y personaliza la configuración de OpenAPI con la información
     * del microservicio (título, descripción y versión).
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-ventas")
                                .description("Microservicio de gestión de ventas")
                                .version("1.0.0")
                );
    }
}
