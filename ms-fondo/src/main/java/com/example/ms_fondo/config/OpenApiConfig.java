package com.example.ms_fondo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Configuración de Swagger/OpenAPI para el microservicio ms-fondo.
 * Expone la documentación interactiva de la API.
 */
public class OpenApiConfig {

    @Bean
    /**
     * Configura la información general de la API de fondos.
     *
     * @return objeto OpenAPI con el título, descripción y versión.
     */
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-fondo")
                                .description("Microservicio de gestión de los fondos")
                                .version("1.0.0")
                );
    }
}