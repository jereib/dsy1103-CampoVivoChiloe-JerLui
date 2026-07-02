package com.example.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8086/swagger-ui.html

// Punto de entrada del microservicio de productos
@SpringBootApplication
public class ProductosApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductosApp.class, args);
    }

}