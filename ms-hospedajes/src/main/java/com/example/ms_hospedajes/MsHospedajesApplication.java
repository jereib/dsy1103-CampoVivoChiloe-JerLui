package com.example.ms_hospedajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8082/swagger-ui.html
@SpringBootApplication
@EnableFeignClients
public class MsHospedajesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHospedajesApplication.class, args);
	}

}
