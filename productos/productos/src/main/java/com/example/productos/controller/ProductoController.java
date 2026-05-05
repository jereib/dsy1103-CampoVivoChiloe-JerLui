package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import com.example.productos.dto.ProductoRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService service; // Inyectamos el Servicio, NO el repositorio

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // OBTENER PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> get(@PathVariable Long id) {
        // Asumiendo que crearás un método obtenerPorId en tu servicio después
        // Por ahora lo dejamos comentado para que no te dé error, enfócate en el POST primero
        // return ResponseEntity.ok(service.obtenerPorId(id));
        return null;
    }

    // CREAR PRODUCTO CON VALIDACIÓN
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        Producto nuevoProducto = service.crearProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }
}