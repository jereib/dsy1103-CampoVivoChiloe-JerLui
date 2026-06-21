package com.example.ms_actividades.controller;

import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.service.ActividadesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades")
@Tag( name = "Actividades", description = "Operaciones relacionadas con las actividades")
public class ActividadesController {

    @Autowired
    private ActividadesService actividadService;

    @GetMapping
    @Operation( summary = "Listar actividades", description = "Obtiene todas las actividades registradas" )
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
    public ResponseEntity<List<ActividadesResponseDTO>> listarActividades(){

        return ResponseEntity.ok(
                actividadService.listarActividades()
        );
    }

    @GetMapping("/{id}")
    @Operation( summary = "Listar actividad por id", description = "Permite obtener una actividad mediante su id" )
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
    public ResponseEntity<?> obtenerActividad(@PathVariable Long id){

        try {

            ActividadesResponseDTO actividad =
                    actividadService.obtenerActividad(id);

            return ResponseEntity.ok(actividad);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    @Operation( summary = "Crear actividad", description = "Permite crear una actividad de acuerdo a sus atributos" )
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
    public ResponseEntity<?> crearActividad(@RequestBody ActividadModel actividad){

        ActividadModel nuevaActividad = actividadService.crearActividad(actividad);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevaActividad);
    }

    @PutMapping("/{id}")
    @Operation( summary = "Actualizar actividad", description = "Permite actualizar una actividad ya existente" )
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
    public ResponseEntity<?> actualizarActividad(
            @PathVariable Long id,
            @RequestBody ActividadModel actividadActualizada){

        try {

            ActividadModel actividad = actividadService
                    .actualizarActividad(id, actividadActualizada);

            return ResponseEntity.ok(actividad);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar actividad", description = "Permite eliminar una actividad ya existente" )
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
    public ResponseEntity<?> eliminarActividad(@PathVariable Long id){

        try {

            actividadService.eliminarActividad(id);

            return ResponseEntity.ok("Actividad eliminada correctamente");

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
