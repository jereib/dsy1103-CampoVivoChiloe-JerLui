package com.example.ms_hospedajes.client;

import com.example.ms_hospedajes.dto.SocioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio ms-socios.
 * Permite consultar datos de socios de forma remota.
 */
@FeignClient(name = "ms-socios", url = "${ms-socios.url:http://localhost:8081}")
public interface SocioClient {

    /**
     * Obtiene un socio por su ID desde ms-socios.
     *
     * @param id identificador del socio.
     * @return datos del socio.
     */
    @GetMapping("/api/v1/socios/{id}")
    SocioDTO obtenerSocio(@PathVariable Long id);
}
