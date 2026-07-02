package com.example.ms_liquidaciones.controller;

import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.service.LiquidacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation
        .Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/liquidaciones")
@Tag( name = "Liquidaciones", description = "Operaciones relacionadas con las liquidaciones")
/**
 * Controlador REST para gestionar liquidaciones.
 * Expone endpoints CRUD y generación de liquidaciones por socio.
 */
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    /**
     * Obtiene todas las liquidaciones registradas.
     *
     * @return lista de liquidaciones.
     */
    @GetMapping
    @Operation( summary = "Listar liquidaciones", description = "Obtiene todas las liquidaciones registradas" )
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
    public ResponseEntity<List<Liquidacion>> listar() {
        return ResponseEntity.ok(liquidacionService.listar());
    }

    /**
     * Genera una liquidación para un socio consultando su deuda en ms-fondo.
     *
     * @param socioId identificador del socio.
     * @return liquidación generada con ingresos, deuda y total.
     */
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

    /**
     * Crea una nueva liquidación manualmente.
     *
     * @param liquidacion datos de la liquidación a crear.
     * @return liquidación creada con estado 201, o mensaje de error si falla.
     */
    @PostMapping
    @Operation( summary = "Crear liquidación", description = "Permite crear una nueva liquidación" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Creado exitosamente"
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
    public ResponseEntity<?> crear(@Valid @RequestBody Liquidacion liquidacion) {
        try {
            Liquidacion nuevo = liquidacionService.guardar(liquidacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Actualiza una liquidación existente por su id.
     *
     * @param id          identificador de la liquidación.
     * @param liquidacion datos actualizados de la liquidación.
     * @return liquidación actualizada, o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    @Operation( summary = "Actualizar liquidación", description = "Actualiza una liquidación existente por su id" )
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
                                         @Valid @RequestBody Liquidacion liquidacion) {
        Liquidacion actualizado = liquidacionService.actualizar(id, liquidacion);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liquidación no encontrada");
        }
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina una liquidación por su id.
     *
     * @param id identificador de la liquidación.
     * @return 204 si se eliminó, 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar liquidación", description = "Elimina una liquidación por su id" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Eliminado exitosamente"
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
        boolean ok = liquidacionService.eliminar(id);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Liquidación no encontrada");
        }
        return ResponseEntity.noContent().build();
    }
}