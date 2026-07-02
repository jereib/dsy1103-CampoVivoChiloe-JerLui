package com.example.ms_liquidaciones.repository;

import com.example.ms_liquidaciones.model.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Liquidacion.
 * Proporciona operaciones CRUD básicas sobre la tabla de liquidaciones.
 */
@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    // métodos CRUD básicos, no necesitamos consultas personalizadas
}
