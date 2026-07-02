package com.example.ms_actividades.client;

import com.example.ms_actividades.dto.SocioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicarse con ms-socios
@FeignClient(name = "ms-socios", url = "${ms-socios.url:http://localhost:8081}")
public interface SocioClient {
    @GetMapping("/api/v1/socios/{id}")
    SocioDTO obtenerSocio(@PathVariable Long id); // trae los datos del socio por su id
}
