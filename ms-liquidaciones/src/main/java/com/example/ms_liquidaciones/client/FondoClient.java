package com.example.ms_liquidaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-fondo", url = "${ms-fondo.url:http://localhost:8089}")
public interface FondoClient {

    @GetMapping("/api/v1/deudas/validar/{socioId}")
    Boolean tieneDeudaActiva(@PathVariable("socioId") Long socioId);
}
