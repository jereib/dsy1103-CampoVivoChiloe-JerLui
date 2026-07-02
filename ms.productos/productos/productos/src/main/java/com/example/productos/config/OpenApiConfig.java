package com.example.productos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentar los endpoints
 * del microservicio de productos.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura la información general de la API que se muestra en Swagger UI.
     *
     * @return objeto OpenAPI con título, descripción y versión
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-productos")
                                .description("Microservicio de gestión de productos")
                                .version("1.0.0")
                );
    }
}