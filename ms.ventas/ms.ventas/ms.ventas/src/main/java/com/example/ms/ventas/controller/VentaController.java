package com.example.ms.ventas.controller;

import com.example.ms.ventas.dto.VentaRequestDTO;
import com.example.ms.ventas.model.Venta;
import com.example.ms.ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    private final VentaService service;

    public VentaController(VentaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody VentaRequestDTO dto) {
        Venta nuevaVenta = service.registrarVenta(dto);
        return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Venta>> listarTodas() {
        return new ResponseEntity<>(service.listarTodas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @Valid @RequestBody VentaRequestDTO dto) {
        Venta ventaActualizada = service.actualizarVenta(id, dto);
        return new ResponseEntity<>(ventaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarVenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}