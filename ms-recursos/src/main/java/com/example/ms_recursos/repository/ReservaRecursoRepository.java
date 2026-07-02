package com.example.ms_recursos.repository;

import com.example.ms_recursos.model.ReservaRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad ReservaRecurso.
 * Proporciona metodos de acceso a datos ademas de los heredados de
 * JpaRepository.
 */
public interface ReservaRecursoRepository extends JpaRepository<ReservaRecurso, Long> {

    /**
     * Busca todas las reservas realizadas por un socio.
     *
     * @param socioId identificador del socio
     * @return reservas del socio
     */
    List<ReservaRecurso> findBySocioId(Long socioId);

    /**
     * Busca reservas por el nombre del recurso.
     *
     * @param nombreRecurso nombre del recurso
     * @return reservas que coinciden con el nombre
     */
    List<ReservaRecurso> findByNombreRecurso(String nombreRecurso);
}