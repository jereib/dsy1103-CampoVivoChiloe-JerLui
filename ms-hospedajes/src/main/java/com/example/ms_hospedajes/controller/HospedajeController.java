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

@RestController
@RequestMapping("/api/v1/hospedajes")
@Tag( name = "Hospedajes", description = "Operaciones relacionadas con los hospedajes")
public class HospedajeController {
    private final HospedajeService hospedajeService;

    // Inyectamos el servicio de hospedajes
    public HospedajeController(HospedajeService hospedajeService){
        this.hospedajeService = hospedajeService;
    }

    // Obtiene todas las relaciones de hospedaje
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

    // Consulta remota al ms-huespedes para obtener datos de un huésped
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

    // Consulta remota al ms-socios para obtener datos de una familia socia
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

    // Crea un hospedaje vinculando un socio y un huésped por sus IDs
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

    // Crea un hospedaje desde un JSON con socioId y huespedId
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

    // Actualiza los IDs de socio y huésped de un hospedaje existente
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

    // Elimina un hospedaje por su id
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
