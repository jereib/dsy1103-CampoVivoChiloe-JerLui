package com.example.ms_recursos.controller;

import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.service.ReservaRecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recursos")
public class ReservaRecursoController {

    @Autowired
    private ReservaRecursoService reservaRecursoService;

    // LISTAR
    @GetMapping
    public ResponseEntity<List<ReservaRecurso>> listar() {
        return ResponseEntity.ok(reservaRecursoService.listar());
    }

    // BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        ReservaRecurso r = reservaRecursoService.buscarPorId(id);

        if (r == null) {
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        return ResponseEntity.ok(r);
    }

    // CREAR (AQUÍ SE VALIDARÁ LA DEUDA)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ReservaRecurso reserva) {
        try {
            ReservaRecurso nueva = reservaRecursoService.guardar(reserva);
            return ResponseEntity.status(201).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        boolean ok = reservaRecursoService.eliminar(id);

        if (!ok) {
            return ResponseEntity.status(404).body("Reserva no encontrada");
        }

        return ResponseEntity.noContent().build();
    }
}