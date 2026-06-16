package com.example.ms_recursos.controller;

import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.service.ReservaRecursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recursos")
@Tag( name = "Socios", description = "Operaciones relacionadas con socios")
public class ReservaRecursoController {

    @Autowired
    private ReservaRecursoService reservaRecursoService;

    @GetMapping
    @Operation( summary = "Listar recursos", description = "Obtiene todos los recursos" )
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

    public ResponseEntity<List<ReservaRecurso>> listar() {
        return ResponseEntity.ok(reservaRecursoService.listar());
    }

    @GetMapping("/{id}")
    @Operation( summary = "Listar recursos mediante id", description = "Obtiene los recursos mediante su id" )
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
        ReservaRecurso r = reservaRecursoService.buscarPorId(id);

        if (r == null) {
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        return ResponseEntity.ok(r);
    }

    @PostMapping
    @Operation( summary = "Crear recurso", description = "Permite crear un recurso de acuerdo a sus atributos" )
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
    public ResponseEntity<?> crear(@RequestBody ReservaRecurso reserva) {
        try {
            ReservaRecurso nueva = reservaRecursoService.guardar(reserva);
            return ResponseEntity.status(201).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation( summary = "Actualizar recurso", description = "Actualizar un recurso mediante su id" )
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
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody ReservaRecurso detallesReserva) {
        try {
            // Verificar si el recurso existe antes de intentar actualizarlo
            ReservaRecurso existente = reservaRecursoService.buscarPorId(id);
            if (existente == null) {
                return ResponseEntity.status(404).body("Reserva no encontrada para actualizar");
            }

            // Asegurar que el objeto lleve el ID correcto de la URL
            detallesReserva.setId(id);

            ReservaRecurso actualizada = reservaRecursoService.guardar(detallesReserva);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar recurso", description = "Elimina un recurso mediante su id" )
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

        boolean ok = reservaRecursoService.eliminar(id);

        if (!ok) {
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        return ResponseEntity.noContent().build();
    }
}