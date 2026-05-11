package com.example.ms_recursos.repository;

import com.example.ms_recursos.model.ReservaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRecursoRepository extends JpaRepository<ReservaRecurso, Long> {

    List<ReservaRecurso> findBySocioId(Long socioId);

    List<ReservaRecurso> findByNombreRecurso(String nombreRecurso);
}