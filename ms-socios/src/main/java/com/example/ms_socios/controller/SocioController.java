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
/**
 * Controlador REST que maneja las operaciones CRUD de familias socias.
 * Expone endpoints bajo /api/v1/socios.
 */
public class SocioController {
    private final SocioService socioService;

    /**
     * Constructor que inyecta la dependencia del servicio.
     *
     * @param socioService servicio con la lógica de negocio de socios
     */
    public SocioController(SocioService socioService){
        this.socioService = socioService;
    }


    /**
     * Obtiene todas las familias socias registradas en el sistema.
     *
     * @return lista de socios en formato JSON
     */
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

    /**
     * Busca una familia socia por su ID.
     *
     * @param id identificador único del socio
     * @return el socio encontrado o un mensaje de error si no existe
     */
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

    /**
     * Filtra las familias socias por su estado (DISPONIBLE, SUSPENDIDO, MANTENIMIENTO).
     *
     * @param estado nombre del estado en string (se convierte a mayúsculas internamente)
     * @return lista de socios con ese estado o error si el estado no es válido
     */
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

    /**
     * Registra una nueva familia socia en el sistema.
     *
     * @param socio datos de la familia socia a crear
     * @return el socio creado o un error si ya existe uno con el mismo nombre
     */
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

    /**
     * Reemplaza todos los datos de una familia socia existente.
     *
     * @param id    identificador del socio a actualizar
     * @param socio objeto con los nuevos datos
     * @return el socio actualizado o un error si no existe
     */
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

    /**
     * Elimina una familia socia del sistema por su ID.
     *
     * @param id identificador del socio a eliminar
     * @return mensaje de éxito o error si el socio no existe
     */
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

    @PatchMapping("/{id}")
    @Operation( summary = "Actualizar parcialmente socios por id", description = "Actualiza algunos campos de una familia socia mediante su id" )
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
     * Actualiza solo los campos enviados en el Map, útil para cambios rápidos
     * sin reemplazar todo el objeto.
     *
     * @param id     identificador del socio a modificar
     * @param campos mapa con los campos a actualizar y sus nuevos valores
     * @return el socio actualizado o un error si no existe
     */
    public ResponseEntity<?> actualizarParcialSocio(@PathVariable Long id, @RequestBody java.util.Map<String, Object> campos) {

        Socio socioActualizado = socioService.actualizarParcial(id, campos);

        if (socioActualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El socio con el ID " + id + " no existe");
        }

        return ResponseEntity.ok(socioActualizado);
    }

}
