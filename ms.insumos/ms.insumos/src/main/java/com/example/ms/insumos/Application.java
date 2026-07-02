package com.example.ms.insumos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8084/swagger-ui.html

/**
 * Clase principal que inicia el microservicio de insumos.
 * Contiene el punto de entrada de la aplicación Spring Boot.
 */
@SpringBootApplication
public class Application {

	/**
	 * Método principal que levanta el contexto de Spring Boot.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
