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

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deudas")
@Tag( name = "Fondos", description = "Operaciones relacionadas con los fondos")
/**
 * Controlador que expone los endpoints para gestionar las deudas de los socios.
 * Opera sobre la entidad DeudaSocio a través del servicio correspondiente.
 */
public class DeudaSocioController {

    @Autowired
    private DeudaSocioService deudaSocioService;

    /**
     * Obtiene todas las deudas registradas en el sistema.
     *
     * @return lista de todas las deudas.
     */
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

    /**
     * Busca una deuda por su identificador.
     *
     * @param id identificador de la deuda.
     * @return la deuda encontrada o un mensaje de error 404.
     */
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

    /**
     * Crea una nueva deuda en el sistema.
     *
     * @param deudaSocio datos de la deuda a crear.
     * @return la deuda creada con código 201.
     */
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
    public ResponseEntity<DeudaSocio> crear(@Valid @RequestBody DeudaSocio deudaSocio) {
        return ResponseEntity.status(201).body(deudaSocioService.guardar(deudaSocio));
    }

    /**
     * Actualiza todos los campos de una deuda existente.
     *
     * @param id         identificador de la deuda a actualizar.
     * @param deudaSocio datos actualizados de la deuda.
     * @return la deuda actualizada o un mensaje de error 404.
     */
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
                                        @Valid @RequestBody DeudaSocio deudaSocio) {

        DeudaSocio actualizada = deudaSocioService.actualizar(id, deudaSocio);

        if (actualizada == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una deuda del sistema por su identificador.
     *
     * @param id identificador de la deuda a eliminar.
     * @return respuesta vacía 204 o mensaje de error 404.
     */
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

    /**
     * Obtiene todas las deudas asociadas a un socio específico.
     *
     * @param socioId identificador del socio.
     * @return lista de deudas del socio.
     */
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

    /**
     * Verifica si un socio tiene al menos una deuda activa.
     *
     * @param socioId identificador del socio.
     * @return true si tiene deuda activa, false en caso contrario.
     */
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

    /**
     * Actualiza parcialmente una deuda. Solo se modifican los campos enviados.
     *
     * @param id     identificador de la deuda.
     * @param campos mapa con los campos a actualizar.
     * @return la deuda actualizada o un mensaje de error 404.
     */
    @PatchMapping("/{id}")
    @Operation( summary = "Actualizar parcialmente deuda", description = "Permite actualizar algunos campos de una deuda mediante su id" )
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
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id,
                                               @RequestBody java.util.Map<String, Object> campos) {

        DeudaSocio actualizada = deudaSocioService.actualizarParcial(id, campos);

        if (actualizada == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(actualizada);
    }
}