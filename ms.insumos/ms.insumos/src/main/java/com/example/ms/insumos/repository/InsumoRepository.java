package com.example.ms.insumos.repository;

import com.example.ms.insumos.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Insumo.
 * Proporciona operaciones CRUD básicas sin necesidad de implementación adicional.
 */
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
}