package com.example.ms_hospedajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada del microservicio de hospedajes.
 * Inicializa la aplicación Spring Boot y habilita los clientes Feign.
 */
//http://localhost:8082/swagger-ui.html
@SpringBootApplication
@EnableFeignClients
public class MsHospedajesApplication {

	/**
	 * Arranca el microservicio de hospedajes.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsHospedajesApplication.class, args);
	}

}
