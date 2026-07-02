package com.example.ms_recursos.repository;

import com.example.ms_recursos.model.ReservaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRecursoRepository extends JpaRepository<ReservaRecurso, Long> {

    // Buscar reservas por el socio que las hizo
    List<ReservaRecurso> findBySocioId(Long socioId);

    // Buscar reservas por el nombre del recurso
    List<ReservaRecurso> findByNombreRecurso(String nombreRecurso);
}