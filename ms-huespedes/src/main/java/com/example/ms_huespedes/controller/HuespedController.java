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
/**
 * Controlador que expone los endpoints para gestionar los huéspedes.
 * Permite realizar operaciones CRUD sobre la entidad Huesped.
 */
public class HuespedController {
    private final HuespedService huespedService;

    /**
     * Constructor que inyecta el servicio de huéspedes.
     *
     * @param huespedService servicio de huéspedes.
     */
    public HuespedController(HuespedService huespedService){
        this.huespedService = huespedService;
    }

    /**
     * Obtiene todos los huéspedes registrados en el sistema.
     *
     * @return lista de huéspedes.
     */
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

    /**
     * Busca un huésped por su identificador.
     *
     * @param id identificador del huésped.
     * @return el huésped encontrado o un mensaje de error 400.
     */
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

    /**
     * Registra un nuevo huésped en el sistema.
     * Si ya existe un huésped con el mismo nombre, retorna error 400.
     *
     * @param huesped datos del huésped a registrar.
     * @return el huésped creado o un mensaje de error.
     */
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

    /**
     * Actualiza todos los datos de un huésped existente.
     *
     * @param id     identificador del huésped.
     * @param huesped datos actualizados del huésped.
     * @return el huésped actualizado o un mensaje de error.
     */
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

    /**
     * Elimina un huésped del sistema por su identificador.
     *
     * @param id identificador del huésped.
     * @return mensaje de confirmación o error.
     */
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

    @PatchMapping("/{id}")
    @Operation( summary = "Actualizar parcialmente huesped", description = "Permite actualizar algunos datos de un huesped ya existente" )
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

    /**
     * Actualiza parcialmente los datos de un huésped. Solo se modifican los campos enviados.
     *
     * @param id     identificador del huésped.
     * @param campos mapa con los campos a actualizar.
     * @return el huésped actualizado o mensaje de error 404.
     */
    public ResponseEntity<?> actualizarParcialHuesped(@PathVariable Long id, @RequestBody java.util.Map<String, Object> campos) {

        Huesped huespedActualizado = huespedService.actualizarParcial(id, campos);

        if (huespedActualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El huésped con el ID " + id + " no existe");
        }

        return ResponseEntity.ok(huespedActualizado);
    }
}
