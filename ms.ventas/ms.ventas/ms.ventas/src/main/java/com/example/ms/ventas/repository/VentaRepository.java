package com.example.ms.ventas.repository;

import com.example.ms.ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositorio básico para operaciones CRUD de ventas
public interface VentaRepository extends JpaRepository<Venta, Long> {
}