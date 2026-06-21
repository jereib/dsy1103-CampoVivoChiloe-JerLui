package com.example.ms_huespedes.controller;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.service.HuespedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/v1/huespedes")
@Tag( name = "Huespedes", description = "Operaciones relacionadas con los huespedes")
public class HuespedController {
    private final HuespedService huespedService;

    public HuespedController(HuespedService huespedService){
        this.huespedService = huespedService;
    }

    @GetMapping
    @Operation( summary = "Listar huespedes", description = "Obtiene a todos los huespedes registrados en el sistema" )
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
    public ResponseEntity<?> listarHuespedes(){
        return ResponseEntity.ok(huespedService.listarHuespedes());

    }

    @GetMapping("/{id}")
    @Operation( summary = "Listar huesped por id", description = "Permite obtener a un huesped mediante su id" )
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
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Huesped huesped = huespedService.buscarPorId(id);

        if (huesped == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El id ingresado no existe");
        }

        return ResponseEntity.ok(huesped);
    }

    @PostMapping
    @Operation( summary = "Crear huesped", description = "Permite registrar a un huesped en el sistema" )
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
    public ResponseEntity<?> guardarHuesped(@Valid @RequestBody Huesped huesped){
        Huesped nuevoHuesped =
                huespedService.guardarHuesped(huesped);

        if(nuevoHuesped == null){
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un huesped con ese nombre");
        }

        return ResponseEntity.ok(nuevoHuesped);
    }

    @PutMapping("/{id}")
    @Operation( summary = "Actualizar huesped", description = "Permite actualizar a un huesped por id" )
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
    public ResponseEntity<?> actualizarHuesped(@PathVariable Long id, @Valid @RequestBody Huesped huesped){
        Huesped huespedActualizado = huespedService.actualizarPorId(id, huesped);

        if(huespedActualizado == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El huesped no existe");
        }

        return ResponseEntity.ok(huespedActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar huesped", description = "Permite eliminar a un huesped registrado mediante su id" )
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
    public ResponseEntity<?> eliminarHuesped(@PathVariable Long id){
        boolean eliminado = huespedService.eliminarHuesped(id);

        if(!eliminado){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El huesped no existe");
        }

        return ResponseEntity.ok("Huesped eliminado correctamente");
    }
}
