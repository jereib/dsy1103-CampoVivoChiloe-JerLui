package com.example.ms_hospedajes.controller;

import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.service.HospedajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone los endpoints para gestionar hospedajes.
 * Permite listar, crear, actualizar y eliminar relaciones entre socios y huéspedes.
 */
@RestController
@RequestMapping("/api/v1/hospedajes")
@Tag( name = "Hospedajes", description = "Operaciones relacionadas con los hospedajes")
public class HospedajeController {
    private final HospedajeService hospedajeService;

    /**
     * Construye el controlador con el servicio de hospedajes.
     *
     * @param hospedajeService servicio que contiene la lógica de negocio.
     */
    public HospedajeController(HospedajeService hospedajeService){
        this.hospedajeService = hospedajeService;
    }

    /**
     * Obtiene todos los hospedajes registrados.
     *
     * @return lista de hospedajes.
     */
    @GetMapping
    @Operation( summary = "Listar hospedajes", description = "Obtiene todos los hospedajes registrados" )
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
    public ResponseEntity<List<HospedajeModel>> listar() {
        return ResponseEntity.ok(hospedajeService.listar());
    }

    /**
     * Consulta un huésped por su ID mediante el microservicio ms-huespedes.
     *
     * @param id identificador del huésped.
     * @return datos del huésped encontrado.
     */
    @GetMapping("/huespedes/{id}")
    @Operation( summary = "Listar huesped por id", description = "Permite obtener un huesped resgitrado mediante su id, se tiene que ejecutar el ms-huespedes" )
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
    public HuespedDTO obtenerHuesped(@PathVariable Long id){
        return hospedajeService.obtenerHuesped(id);
    }

    /**
     * Consulta un socio por su ID mediante el microservicio ms-socios.
     *
     * @param id identificador del socio.
     * @return datos del socio encontrado.
     */
    @GetMapping("/socios/{id}")
    @Operation( summary = "Listar socios por id", description = "Permite obtener a una familia socia registrada mediante el id, se tiene que ejecutar el ms-socios" )
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
    public SocioDTO obtenerSocio(@PathVariable Long id){
        return hospedajeService.obtenerSocio(id);
    }

    /**
     * Crea un hospedaje a partir de los IDs de socio y huésped en la URL.
     *
     * @param socioId   identificador del socio.
     * @param huespedId identificador del huésped.
     * @return mensaje de confirmación o error.
     */
    @GetMapping("/crear/{socioId}/{huespedId}")
    @Operation( summary = "Crear hospedaje", description = "Permite crear un hospedaje relacionando un huesped a una familia socia" )
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
    public ResponseEntity<?> crearHospedaje(
            @PathVariable Long socioId,
            @PathVariable Long huespedId){

        try{

            String mensaje =
                    hospedajeService.crearHospedaje(socioId, huespedId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mensaje);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: huesped o socio no encontrado");
        }
    }

    /**
     * Crea un hospedaje a partir de un JSON con socioId y huespedId.
     *
     * @param hospedaje objeto con los datos del hospedaje.
     * @return mensaje de confirmación o error.
     */
    @PostMapping
    @Operation( summary = "Crear hospedaje", description = "Permite crear un hospedaje con los IDs de socio y huésped" )
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
    public ResponseEntity<?> crear(@Valid @RequestBody HospedajeModel hospedaje) {
        try {
            String mensaje = hospedajeService.crearHospedaje(
                    hospedaje.getSocioId(), hospedaje.getHuespedId());
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: huesped o socio no encontrado");
        }
    }

    /**
     * Actualiza los IDs de socio y huésped de un hospedaje existente.
     *
     * @param id        identificador del hospedaje.
     * @param hospedaje objeto con los nuevos datos.
     * @return hospedaje actualizado o mensaje de error.
     */
    @PutMapping("/{id}")
    @Operation( summary = "Actualizar hospedaje", description = "Actualiza un hospedaje existente por su id" )
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
                                         @Valid @RequestBody HospedajeModel hospedaje) {
        HospedajeModel actualizado = hospedajeService.actualizar(id, hospedaje);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hospedaje no encontrado");
        }
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un hospedaje por su identificador.
     *
     * @param id identificador del hospedaje.
     * @return respuesta vacía si se elimina, o error si no existe.
     */
    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar hospedaje", description = "Elimina un hospedaje por su id" )
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
        boolean ok = hospedajeService.eliminar(id);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Hospedaje no encontrado");
        }
        return ResponseEntity.noContent().build();
    }
}
