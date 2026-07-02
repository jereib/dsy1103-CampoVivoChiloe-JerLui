package com.example.ms_recursos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Cliente Feign para comunicarse con ms-fondo
@FeignClient(name = "ms-fondo", url = "${ms-fondo.url:http://localhost:8089}")
public interface FondoClient {

    // Consulta si un socio tiene deuda activa
    @GetMapping("/api/v1/deudas/validar/{socioId}")
    Boolean tieneDeudaActiva(@PathVariable("socioId") Long socioId);
}
