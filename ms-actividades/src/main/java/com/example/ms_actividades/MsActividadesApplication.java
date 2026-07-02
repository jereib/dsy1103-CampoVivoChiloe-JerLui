package com.example.ms_actividades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada del microservicio de actividades.
 * Levanta el contexto de Spring Boot y activa la comunicación
 * con otros microservicios a través de Feign.
 */
@SpringBootApplication
@EnableFeignClients
public class MsActividadesApplication {

	/**
	 * Arranca la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsActividadesApplication.class, args);
	}

}
