package com.example.ms_liquidaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8090/swagger-ui.html
@SpringBootApplication
@EnableFeignClients // habilita Feign para llamar a ms-fondo
/**
 * Clase principal del microservicio de liquidaciones.
 * Inicia la aplicación Spring Boot y habilita los clientes Feign para
 * comunicarse con otros microservicios.
 */
public class MsLiquidacionesApplication {

	/**
	 * Punto de entrada de la aplicación.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MsLiquidacionesApplication.class, args);
	}

}
