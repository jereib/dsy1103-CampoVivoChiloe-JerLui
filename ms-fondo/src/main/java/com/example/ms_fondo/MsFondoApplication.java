package com.example.ms_fondo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8089/swagger-ui.html
@SpringBootApplication
/**
 * Clase principal del microservicio ms-fondo.
 * Inicia la aplicación Spring Boot y sus componentes.
 */
public class MsFondoApplication {

	/**
	 * Punto de entrada del microservicio de fondos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsFondoApplication.class, args);
	}

}
