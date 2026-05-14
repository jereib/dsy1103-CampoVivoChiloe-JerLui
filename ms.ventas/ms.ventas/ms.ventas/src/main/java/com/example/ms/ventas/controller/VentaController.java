package com.example.ms.ventas.controller;

import com.example.ms.ventas.dto.VentaRequestDTO;
import com.example.ms.ventas.model.Venta;
import com.example.ms.ventas.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}