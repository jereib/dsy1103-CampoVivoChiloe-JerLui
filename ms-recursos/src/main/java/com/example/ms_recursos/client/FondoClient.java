package com.example.ms_recursos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio ms-fondo.
 * Permite consultar informacion de deudas de los socios.
 */
@FeignClient(name = "ms-fondo", url = "${ms-fondo.url:http://localhost:8089}")
public interface FondoClient {

    /**
     * Consulta si un socio tiene una deuda activa en el sistema de fondos.
     *
     * @param socioId identificador del socio
     * @return true si tiene deuda activa, false en caso contrario
     */
    @GetMapping("/api/v1/deudas/validar/{socioId}")
    Boolean tieneDeudaActiva(@PathVariable("socioId") Long socioId);
}
