package com.example.ms.insumos.controller;

import com.example.ms.insumos.dto.InsumoRequestDTO;
import com.example.ms.insumos.model.Insumo;
import com.example.ms.insumos.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insumos")
@Tag( name = "Insumos", description = "Operaciones relacionadas con los insumos")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation( summary = "Crear insumo", description = "Permite crear un insumo con sus atributos" )
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
    public ResponseEntity<Insumo> crear(@Valid @RequestBody InsumoRequestDTO dto) {
        Insumo nuevoInsumo = service.crearInsumo(dto);
        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation( summary = "Obtener insumo por id", description = "Permite obtener un insumo existente por su id" )
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
    public ResponseEntity<Insumo> obtenerPorId(@PathVariable Long id) {
        Insumo insumo = service.obtenerPorId(id);
        return new ResponseEntity<>(insumo, HttpStatus.OK);
    }

    @GetMapping
    @Operation( summary = "Listar insumos", description = "Obtiene todos los insumos registrados" )
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
    public ResponseEntity<List<Insumo>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation( summary = "Actualizar insumo", description = "Permite actualizar un insumo por su id" )
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
    public ResponseEntity<Insumo> actualizar(@PathVariable Long id, @Valid @RequestBody InsumoRequestDTO dto) {
        Insumo insumoActualizado = service.actualizarInsumo(id, dto);
        return new ResponseEntity<>(insumoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar insumo", description = "Permite eliminar un insumo por su id" )
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
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarInsumo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un insumo", description = "Permite modificar únicamente los atributos enviados en el cuerpo de la petición utilizando el id del insumo")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actualización parcial exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida o datos erróneos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Insumo no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody java.util.Map<String, Object> campos) {
        try {
            Insumo insumoParcial = service.actualizarParcial(id, campos);
            return new ResponseEntity<>(insumoParcial, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al procesar la actualización parcial");
        }
    }
}