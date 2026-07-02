package com.example.ms_recursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8088/swagger-ui.html

// Punto de entrada del microservicio de recursos
@SpringBootApplication
@EnableFeignClients
public class MsRecursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRecursosApplication.class, args);
	}

}
