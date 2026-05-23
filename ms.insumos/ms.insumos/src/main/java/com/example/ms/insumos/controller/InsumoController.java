package com.example.ms.insumos.controller;

import com.example.ms.insumos.dto.InsumoRequestDTO;
import com.example.ms.insumos.model.Insumo;
import com.example.ms.insumos.service.InsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/insumos")
public class InsumoController {

    private final InsumoService service;

    public InsumoController(InsumoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Insumo> crear(@Valid @RequestBody InsumoRequestDTO dto) {
        Insumo nuevoInsumo = service.crearInsumo(dto);
        return new ResponseEntity<>(nuevoInsumo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> obtenerPorId(@PathVariable Long id) {
        Insumo insumo = service.obtenerPorId(id);
        return new ResponseEntity<>(insumo, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Insumo>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumo> actualizar(@PathVariable Long id, @Valid @RequestBody InsumoRequestDTO dto) {
        Insumo insumoActualizado = service.actualizarInsumo(id, dto);
        return new ResponseEntity<>(insumoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarInsumo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}