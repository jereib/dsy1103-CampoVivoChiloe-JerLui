package com.example.ms_actividades.repository;

import com.example.ms_actividades.model.ActividadModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad ActividadModel.
 * Proporciona operaciones CRUD básicas sobre la tabla de actividades.
 */
public interface ActividadesRepositorio extends JpaRepository<ActividadModel, Long> {
}
