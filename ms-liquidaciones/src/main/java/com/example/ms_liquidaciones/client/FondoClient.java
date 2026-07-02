package com.example.ms_liquidaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio ms-fondo.
 * Permite consultar si un socio tiene deuda activa.
 */
@FeignClient(name = "ms-fondo", url = "${ms-fondo.url:http://localhost:8089}")
public interface FondoClient {

    /**
     * Consulta si un socio tiene deuda activa en ms-fondo.
     *
     * @param socioId identificador del socio.
     * @return true si el socio tiene deuda activa, false en caso contrario.
     */
    @GetMapping("/api/v1/deudas/validar/{socioId}")
    Boolean tieneDeudaActiva(@PathVariable("socioId") Long socioId);
}
