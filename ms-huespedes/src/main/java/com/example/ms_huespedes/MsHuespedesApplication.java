package com.example.ms_huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//http://localhost:8083/swagger-ui.html
@SpringBootApplication
public class MsHuespedesApplication {

	// Punto de entrada del microservicio de huéspedes
	public static void main(String[] args) {
		SpringApplication.run(MsHuespedesApplication.class, args);
	}

}
