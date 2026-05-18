package com.example.ms_huespedes.controller;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.service.HuespedService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/v1/huespedes")
public class HuespedController {
    private final HuespedService huespedService;

    public HuespedController(HuespedService huespedService){
        this.huespedService = huespedService;
    }

    @GetMapping
    public ResponseEntity<?> listarHuespedes(){
        return ResponseEntity.ok(huespedService.listarHuespedes());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Huesped huesped = huespedService.buscarPorId(id);

        if (huesped == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El id ingresado no existe");
        }

        return ResponseEntity.ok(huesped);
    }

    @PostMapping
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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarHuesped(@PathVariable Long id, @Valid @RequestBody Huesped huesped){
        Huesped huespedActualizado = huespedService.actualizarPorId(id, huesped);

        if(huespedActualizado == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El huesped no existe");
        }

        return ResponseEntity.ok(huespedActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHuesped(@PathVariable Long id){
        boolean eliminado = huespedService.eliminarHuesped(id);

        if(!eliminado){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El huesped no existe");
        }

        return ResponseEntity.ok("Huesped eliminado correctamente");
    }
}
