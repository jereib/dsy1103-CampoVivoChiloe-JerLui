package com.example.ms_liquidaciones.controller;

import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.service.LiquidacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation
        .Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/liquidaciones")
@Tag( name = "Liquidaciones", description = "Operaciones relacionadas con las liquidaciones")
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    @GetMapping("/{socioId}")
    @Operation( summary = "Listar liquidaciones por socio", description = "Obtiene todas las liquidaciones registradas mediante el id de la familia socia" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Liquidacion> generar(
            @PathVariable Long socioId) {

        return ResponseEntity.ok(
                liquidacionService.generarLiquidacion(socioId)
        );
    }
}