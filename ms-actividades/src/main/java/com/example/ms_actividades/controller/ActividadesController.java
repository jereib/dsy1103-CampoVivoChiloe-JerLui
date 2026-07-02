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

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controlador REST que expone los endpoints para gestionar actividades.
 * Permite listar, obtener, crear, actualizar y eliminar actividades.
 */
@RestController
@RequestMapping("/api/v1/actividades")
@Tag( name = "Actividades", description = "Operaciones relacionadas con las actividades")
public class ActividadesController {

    @Autowired
    private ActividadesService actividadService;

    /**
     * Obtiene todas las actividades registradas.
     *
     * @return lista de actividades con el nombre del socio incluido.
     */
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

    /**
     * Obtiene una actividad por su id, incluyendo el nombre del socio.
     *
     * @param id identificador de la actividad.
     * @return actividad encontrada o un 404 si no existe.
     */
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
            // si no existe la actividad, devolvemos 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /**
     * Crea una nueva actividad y la guarda en la base de datos.
     *
     * @param actividad datos de la actividad a crear.
     * @return la actividad creada con su id asignado.
     */
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
    public ResponseEntity<?> crearActividad(@Valid @RequestBody ActividadModel actividad){

        ActividadModel nuevaActividad = actividadService.crearActividad(actividad);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevaActividad);
    }

    /**
     * Reemplaza todos los campos de una actividad existente.
     *
     * @param id                  identificador de la actividad.
     * @param actividadActualizada datos actualizados de la actividad.
     * @return la actividad actualizada o un 404 si no se encuentra.
     */
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
            @Valid @RequestBody ActividadModel actividadActualizada){

        try {

            ActividadModel actividad = actividadService
                    .actualizarActividad(id, actividadActualizada);

            return ResponseEntity.ok(actividad);

        } catch (RuntimeException e){
            // si no existe, 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /**
     * Elimina una actividad por su id.
     *
     * @param id identificador de la actividad a eliminar.
     * @return mensaje de éxito o un 404 si no se encuentra.
     */
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

    /**
     * Actualiza solo los campos especificados de una actividad.
     *
     * @param id     identificador de la actividad.
     * @param campos mapa con los campos a actualizar.
     * @return la actividad con los cambios aplicados o 404 si no existe.
     */
    @PatchMapping("/{id}")
    @Operation( summary = "Actualizar parcialmente actividad", description = "Permite actualizar algunos datos de una actividad ya existente" )
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

    public ResponseEntity<?> actualizarParcialActividad(
            @PathVariable Long id,
            @RequestBody java.util.Map<String, Object> campos) {

        try {
            // Delegamos la actualización parcial a la capa de servicio
            ActividadModel actividadActualizada = actividadService.actualizarParcial(id, campos);

            return ResponseEntity.ok(actividadActualizada);

        } catch (RuntimeException e) {
            // Captura el error si la actividad no existe, devolviendo un 404 con el mensaje correspondiente
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
