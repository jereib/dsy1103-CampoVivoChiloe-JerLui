package com.example.ms_liquidaciones.service;

import com.example.ms_liquidaciones.client.FondoClient;
import com.example.ms_liquidaciones.model.Liquidacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LiquidacionService {

    private static final Logger log = LoggerFactory.getLogger(LiquidacionService.class);

    private final FondoClient fondoClient;

    public LiquidacionService(FondoClient fondoClient) {
        this.fondoClient = fondoClient;
    }

    public Liquidacion generarLiquidacion(Long socioId) {
        log.info("Generando liquidación para socio ID: {}", socioId);

        Boolean tieneDeuda;
        try {
            tieneDeuda = fondoClient.tieneDeudaActiva(socioId);
            log.debug("Respuesta de ms-fondo para socio ID {}: tieneDeuda={}", socioId, tieneDeuda);
        } catch (Exception e) {
            log.error("Error al consultar ms-fondo para socio ID {}: {}", socioId, e.getMessage());
            throw new RuntimeException("No se pudo obtener el estado de deuda del socio.");
        }

        double ingresos = 200000;
        double deuda = tieneDeuda != null && tieneDeuda ? 50000 : 0;
        double total = ingresos - deuda;

        log.info("Liquidación calculada para socio ID {}: ingresos={}, deuda={}, total={}",
                socioId, ingresos, deuda, total);

        return new Liquidacion(socioId, ingresos, deuda, total);
    }
}