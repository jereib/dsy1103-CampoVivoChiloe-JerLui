package com.example.ms_recursos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura Swagger/OpenAPI para generar la documentacion de los endpoints
 * del microservicio.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Crea y configura el bean de OpenAPI con la informacion basica del
     * microservicio (titulo, descripcion, version).
     *
     * @return configuracion de OpenAPI
     */
    @Bean
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-recursos")
                                .description("Microservicio de gestión de recursos")
                                .version("1.0.0")
                );
    }
}
