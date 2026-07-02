package com.example.ms.ventas.repository;

import com.example.ms.ventas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad Venta.
 * Proporciona operaciones CRUD básicas sobre las ventas.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
}