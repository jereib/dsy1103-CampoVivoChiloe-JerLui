package com.example.ms_fondo.controller;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.service.DeudaSocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deudas")
public class DeudaSocioController {

    @Autowired
    private DeudaSocioService deudaSocioService;

    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<DeudaSocio>> listar() {
        return ResponseEntity.ok(deudaSocioService.listar());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        DeudaSocio deuda = deudaSocioService.buscarPorId(id);

        if (deuda == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(deuda);
    }

    // CREAR
    @PostMapping
    public ResponseEntity<DeudaSocio> crear(@RequestBody DeudaSocio deudaSocio) {
        return ResponseEntity.status(201).body(deudaSocioService.guardar(deudaSocio));
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody DeudaSocio deudaSocio) {

        DeudaSocio actualizada = deudaSocioService.actualizar(id, deudaSocio);

        if (actualizada == null) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.ok(actualizada);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        boolean ok = deudaSocioService.eliminar(id);

        if (!ok) {
            return ResponseEntity.status(404).body("Deuda no encontrada");
        }

        return ResponseEntity.noContent().build();
    }

    // BUSCAR DEUDAS POR SOCIO
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<DeudaSocio>> buscarPorSocio(@PathVariable Long socioId) {
        return ResponseEntity.ok(deudaSocioService.buscarPorSocio(socioId));
    }

    // VALIDAR SI TIENE DEUDA ACTIVA (CLAVE PARA OTROS MICROSERVICIOS)
    @GetMapping("/validar/{socioId}")
    public ResponseEntity<Boolean> tieneDeuda(@PathVariable Long socioId) {
        return ResponseEntity.ok(deudaSocioService.tieneDeudaActiva(socioId));
    }
}