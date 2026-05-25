package com.example.ms_liquidaciones.controller;

import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.service.LiquidacionService;
import org.springframework.beans.factory.annotation
        .Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/liquidaciones")
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    @GetMapping("/{socioId}")
    public ResponseEntity<Liquidacion> generar(
            @PathVariable Long socioId) {

        return ResponseEntity.ok(
                liquidacionService.generarLiquidacion(socioId)
        );
    }
}