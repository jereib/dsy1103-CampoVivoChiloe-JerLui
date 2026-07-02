package com.example.ms.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8087/swagger-ui.html

/**
 * Punto de entrada del microservicio de ventas.
 * Levanta el contexto de Spring Boot y habilita los clientes Feign
 * para la comunicación con otros microservicios.
 */
@SpringBootApplication
@EnableFeignClients
public class Application {
	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}