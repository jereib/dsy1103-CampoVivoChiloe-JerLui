package com.example.ms_socios.controller;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/socios")
public class SocioController {
    private final SocioService socioService;

    public SocioController(SocioService socioService){
        this.socioService = socioService;
    }

    //mostrar familias socias
    @GetMapping
    public ResponseEntity<?> listarSocios(){
        return ResponseEntity.ok(socioService.listarSocios());
    }

    //buscar por id de las familias socias
    @GetMapping("/{id}")
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
    public ResponseEntity<?> eliminarSocio(@PathVariable Long id) {

        boolean eliminado = socioService.eliminarSocio(id);

        if(!eliminado){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El socio no existe");
        }

        return ResponseEntity.ok("Socio eliminado correctamente");
    }

}
