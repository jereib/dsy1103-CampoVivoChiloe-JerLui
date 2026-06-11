package com.example.ms_socios.controller;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.service.SocioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/socios")
@Tag( name = "Socios", description = "Operaciones relacionadas con socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService){
        this.socioService = socioService;
    }


    //mostrar familias socias
    @GetMapping
    @Operation( summary = "Listar socios", description = "Obtiene todas las familias socias registradas" )
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
    public ResponseEntity<?> listarSocios(){
        return ResponseEntity.ok(socioService.listarSocios());
    }

    //buscar por id de las familias socias
    @GetMapping("/{id}")
    @Operation( summary = "Listar socios por id", description = "Obtiene una familia socia registrada mediante su id" )
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
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        Socio socio = socioService.buscarPorId(id);

        if(socio == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El id ingresado no existe");
        }

        return ResponseEntity.ok(socio);
    }

    //buscar por el estado de las familias socias
    @GetMapping("/estado/{estado}")
    @Operation( summary = "Listar socios por estado", description = "Obtiene una familia socia registrada mediante su estado" )
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
    public ResponseEntity<?> obtenerSocioPorEstado(@PathVariable String estado){
        try {

            Estado estadoEnum = Estado.valueOf(estado.toUpperCase());

            return ResponseEntity.ok(
                    socioService.buscarPorEstado(estadoEnum)
            );

        } catch (IllegalArgumentException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El estado ingresado no existe");
        }
    }

    //guardar familias socias
    @PostMapping
    @Operation( summary = "ingresa una familia socia", description = "se ingresa una familia socia nueva con sus atributos correspondientes" )
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
    public ResponseEntity<?> guardarSocio(@Valid @RequestBody Socio socio){
        Socio nuevoSocio =
                socioService.guardarSocio(socio);

        if(nuevoSocio == null){
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un socio con ese nombre");
        }

        return ResponseEntity.ok(nuevoSocio);
    }

    //actualizar familias socias por id
    @PutMapping("/{id}")
    @Operation( summary = "Actualiza socios por id", description = "Actualiza una familia socia mediante su id" )
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
    public ResponseEntity<?> actualizarSocio(@PathVariable Long id,@Valid @RequestBody Socio socio) {

        Socio socioActualizado = socioService.actualizarPorId(id, socio);

        if(socioActualizado == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El socio no existe");
        }

        return ResponseEntity.ok(socioActualizado);
    }

    //eliminar familias socias por id
    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar socios por id", description = "Elimina una familia socia registrada mediante su id" )
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
    public ResponseEntity<?> eliminarSocio(@PathVariable Long id) {

        boolean eliminado = socioService.eliminarSocio(id);

        if(!eliminado){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El socio no existe");
        }

        return ResponseEntity.ok("Socio eliminado correctamente");
    }

}
