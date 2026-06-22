package com.example.ms_liquidaciones.service;

import com.example.ms_liquidaciones.model.Liquidacion;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LiquidacionService {

    private RestTemplate restTemplate = new RestTemplate();

    private String urlFondo = "http://localhost:8088/api/v1/deudas/validar/";

    public Liquidacion generarLiquidacion(Long socioId) {

        Boolean tieneDeuda = restTemplate.getForObject(
                urlFondo + socioId,
                Boolean.class
        );

        double ingresos = 200000;
        double deuda = tieneDeuda != null && tieneDeuda ? 50000 : 0;
        double total = ingresos - deuda;

        return new Liquidacion(socioId, ingresos, deuda, total);
    }
}