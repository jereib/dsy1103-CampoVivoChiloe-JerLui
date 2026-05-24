package com.example.ms_actividades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsActividadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsActividadesApplication.class, args);
	}

}
