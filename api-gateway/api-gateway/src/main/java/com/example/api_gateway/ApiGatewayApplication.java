// Paquete del API Gateway, aquí estará la config de rutas y filtros
package com.example.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del API Gateway.
 * Arranca la aplicación Spring Boot y levanta el contexto.
 */
@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * Punto de entrada del microservicio.
	 * Inicia Spring Boot con la configuración del gateway.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		// Levanta el contexto de Spring y arranca todo
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
