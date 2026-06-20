package com.example.ms_fondo.controller;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.service.DeudaSocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deudas")
@Tag( name = "Fondos", description = "Operaciones relacionadas con los fondos")
public class DeudaSocioController {

    @Autowired
    private DeudaSocioService deudaSocioService;

    @GetMapping
    @Operation( summary = "Listar fondos", description = "Obtiene todos los fondos registrados" )
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
    public ResponseEntity<List<DeudaSocio>> listar() {
        return ResponseEntity.ok(deudaSocioService.listar());
    }

    @GetMapping("/{id}")
    @Operation( summary = "Obtener fondo por id", description = "Permite obtener un fondo mediante su id" )
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
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        DeudaSocio deuda = deudaSocioService.buscarPorId(id);

        if (deuda == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(deuda);
    }

    @PostMapping
    @Operation( summary = "Crear fondo", description = "Permite crear un fondo de acuerdo a sus atributos" )
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
    public ResponseEntity<DeudaSocio> crear(@RequestBody DeudaSocio deudaSocio) {
        return ResponseEntity.status(201).body(deudaSocioService.guardar(deudaSocio));
    }

    @PutMapping("/{id}")
    @Operation( summary = "Actualizar fondo", description = "Permite actualizar un fondo ya existente en el sistema" )
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
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody DeudaSocio deudaSocio) {

        DeudaSocio actualizada = deudaSocioService.actualizar(id, deudaSocio);

        if (actualizada == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar fondo", description = "Permite eliminar un fondo ya existente en el sistema" )
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
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        boolean ok = deudaSocioService.eliminar(id);

        if (!ok) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.noContent().build();
    }

    // BUSCAR DEUDAS POR SOCIO
    @GetMapping("/socio/{socioId}")
    @Operation( summary = "Obtener fondo por id de familia socia", description = "Obtiene todas las deudas relacionadas a una familia socia mediante su id")
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
    public ResponseEntity<List<DeudaSocio>> buscarPorSocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(deudaSocioService.buscarPorSocio(socioId));
    }

    @GetMapping("/validar/{socioId}")
    @Operation( summary = "Validar deuda", description = "Permite validar si una deuda esta activa en una familia socia" )
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
    public ResponseEntity<Boolean> tieneDeuda(@PathVariable Long socioId) {
        return ResponseEntity.ok(deudaSocioService.tieneDeudaActiva(socioId));
    }
}