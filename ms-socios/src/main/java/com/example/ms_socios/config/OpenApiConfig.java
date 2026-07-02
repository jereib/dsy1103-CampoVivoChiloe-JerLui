package com.example.ms_socios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Configuración de Swagger/OpenAPI para el microservicio.
 * Define la información que aparece en la interfaz de Swagger UI.
 */
public class OpenApiConfig {

    @Bean
    /**
     * Crea y personaliza la documentación OpenAPI del microservicio.
     *
     * @return objeto OpenAPI con el título, descripción y versión
     */
    public OpenAPI configurarOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Ms-socios")
                                .description("Microservicio de gestión de familias socias")
                                .version("1.0.0")
                );
    }
}
