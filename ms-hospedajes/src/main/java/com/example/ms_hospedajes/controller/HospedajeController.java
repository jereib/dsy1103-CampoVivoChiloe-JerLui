package com.example.ms_hospedajes.controller;

import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.service.HospedajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospedajes")
@Tag( name = "Hospedajes", description = "Operaciones relacionadas con los hospedajes")
public class HospedajeController {
    private final HospedajeService hospedajeService;

    public HospedajeController(HospedajeService hospedajeService){
        this.hospedajeService = hospedajeService;
    }

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
}
