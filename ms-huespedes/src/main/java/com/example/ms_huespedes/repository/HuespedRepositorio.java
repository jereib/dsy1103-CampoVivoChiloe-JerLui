package com.example.ms_huespedes.repository;

import com.example.ms_huespedes.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HuespedRepositorio extends JpaRepository<Huesped, Long> {
    boolean existsByNombreCompletoIgnoreCase(String nombreCompleto);
}
