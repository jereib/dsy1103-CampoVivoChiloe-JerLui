package com.example.ms_socios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8081/swagger-ui.html
@SpringBootApplication
/**
 * Clase principal del microservicio ms-socios.
 * Se encarga de arrancar la aplicación Spring Boot.
 */
public class MsSociosApplication {

	/**
	 * Punto de entrada del microservicio de socios.
	 * Inicia la aplicación Spring Boot y levanta el contexto.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsSociosApplication.class, args);
	}

}
