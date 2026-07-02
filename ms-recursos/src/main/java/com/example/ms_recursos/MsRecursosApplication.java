package com.example.ms_recursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8088/swagger-ui.html

/**
 * Punto de entrada del microservicio de recursos.
 * Levanta el contexto de Spring Boot y habilita los clientes Feign.
 */
@SpringBootApplication
@EnableFeignClients
public class MsRecursosApplication {

	/**
	 * metodo principal que arranca la aplicacion Spring Boot.
	 *
	 * @param args argumentos de linea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsRecursosApplication.class, args);
	}

}
