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

/**
 * Controlador que expone los endpoints REST para la gestión de insumos.
 * Permite crear, consultar, actualizar y eliminar insumos del sistema.
 */
@RestController
@RequestMapping("/api/v1/insumos")
@Tag( name = "Insumos", description = "Operaciones relacionadas con los insumos")
public class InsumoController {

    private final InsumoService service;

    /**
     * Constructor que inyecta el servicio de insumos.
     *
     * @param service servicio con la lógica de negocio de insumos
     */
    public InsumoController(InsumoService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo insumo a partir de los datos enviados en el cuerpo de la petición.
     *
     * @param dto objeto con los datos del insumo a crear
     * @return el insumo creado con código 201 (Created)
     */
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

    /**
     * Obtiene un insumo por su identificador único.
     *
     * @param id identificador del insumo
     * @return el insumo encontrado con código 200
     */
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

    /**
     * Lista todos los insumos registrados en el sistema.
     *
     * @return lista de insumos con código 200
     */
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

    /**
     * Reemplaza completamente los datos de un insumo existente.
     *
     * @param id  identificador del insumo a actualizar
     * @param dto objeto con los nuevos datos del insumo
     * @return el insumo actualizado con código 200
     */
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

    /**
     * Elimina un insumo del sistema por su identificador.
     *
     * @param id identificador del insumo a eliminar
     * @return código 204 (No Content) si la operación fue exitosa
     */
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

    /**
     * Actualiza solo los campos enviados en el cuerpo de la petición (PATCH).
     * Si el insumo no existe retorna 404; si hay un error de datos retorna 400.
     *
     * @param id     identificador del insumo a modificar
     * @param campos mapa con los campos a actualizar y sus nuevos valores
     * @return el insumo modificado o un mensaje de error
     */
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