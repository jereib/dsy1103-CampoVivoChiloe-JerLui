package com.example.ms_huespedes.repository;

import com.example.ms_huespedes.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repositorio JPA para la entidad Huesped.
 * Proporciona operaciones básicas de base de datos y una consulta para validar duplicados.
 */
public interface HuespedRepositorio extends JpaRepository<Huesped, Long> {
    /**
     * Verifica si ya existe un huésped con el nombre dado, ignorando mayúsculas/minúsculas.
     *
     * @param nombreCompleto nombre completo a verificar.
     * @return true si ya existe un huésped con ese nombre.
     */
    boolean existsByNombreCompletoIgnoreCase(String nombreCompleto);
}
