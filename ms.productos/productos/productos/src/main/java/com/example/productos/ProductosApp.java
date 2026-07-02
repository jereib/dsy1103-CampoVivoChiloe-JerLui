package com.example.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8086/swagger-ui.html

/**
 * Clase principal del microservicio de productos.
 * Inicia la aplicación Spring Boot y levanta todos los componentes.
 */
@SpringBootApplication
public class ProductosApp {

    /**
     * Punto de entrada de la aplicación. Ejecuta el contexto de Spring.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductosApp.class, args);
    }

}