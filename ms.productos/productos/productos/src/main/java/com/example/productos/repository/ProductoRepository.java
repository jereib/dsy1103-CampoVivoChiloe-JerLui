package com.example.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.productos.model.Producto; // <-- ESTA ES LA LÍNEA QUE FALTA

/**
 * Repositorio JPA para la entidad Producto.
 * Proporciona operaciones básicas de base de datos como guardar, buscar, listar y eliminar.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}