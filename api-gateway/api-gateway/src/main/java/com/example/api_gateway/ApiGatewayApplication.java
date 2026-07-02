// Paquete del API Gateway, aquí estará la config de rutas y filtros
package com.example.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Anotación que marca esta clase como la app Spring Boot
@SpringBootApplication
public class ApiGatewayApplication {

	// Punto de entrada del microservicio
	public static void main(String[] args) {
		// Levanta el contexto de Spring y arranca todo
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
