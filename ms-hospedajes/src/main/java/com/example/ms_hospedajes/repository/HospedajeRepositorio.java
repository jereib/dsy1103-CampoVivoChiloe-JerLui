package com.example.ms_hospedajes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_hospedajes.model.HospedajeModel;
/**
 * Repositorio JPA para las operaciones CRUD de hospedajes en la base de datos.
 */
public interface HospedajeRepositorio extends JpaRepository<HospedajeModel, Long> {
}
