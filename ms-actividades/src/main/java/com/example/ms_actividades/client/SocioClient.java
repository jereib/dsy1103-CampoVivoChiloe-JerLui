package com.example.ms_actividades.client;

import com.example.ms_actividades.dto.SocioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio ms-socios.
 * Permite obtener los datos de una familia socia por su id.
 */
@FeignClient(name = "ms-socios", url = "${ms-socios.url:http://localhost:8081}")
public interface SocioClient {
    /**
     * Obtiene los datos de un socio según su id.
     *
     * @param id identificador del socio.
     * @return DTO con los datos del socio.
     */
    @GetMapping("/api/v1/socios/{id}")
    SocioDTO obtenerSocio(@PathVariable Long id);
}
