package com.example.ms_hospedajes.client;

import com.example.ms_hospedajes.dto.HuespedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio ms-huespedes.
 * Permite consultar datos de huéspedes de forma remota.
 */
@FeignClient(name = "ms-huespedes", url = "${ms-huespedes.url:http://localhost:8083}")
public interface HuespedClient {

    /**
     * Obtiene un huésped por su ID desde ms-huespedes.
     *
     * @param id identificador del huésped.
     * @return datos del huésped.
     */
    @GetMapping("/api/v1/huespedes/{id}")
    HuespedDTO obtenerHuesped(@PathVariable Long id);
}
