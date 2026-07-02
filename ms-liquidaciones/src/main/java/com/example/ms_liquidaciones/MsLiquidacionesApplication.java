package com.example.ms_liquidaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//http://localhost:8090/swagger-ui.html
@SpringBootApplication
@EnableFeignClients // habilita Feign para llamar a ms-fondo
public class MsLiquidacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLiquidacionesApplication.class, args);
	}

}
