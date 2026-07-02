package com.example.ms_fondo.repository;

import com.example.ms_fondo.model.DeudaSocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad DeudaSocio.
 * Proporciona operaciones básicas de base de datos y consultas personalizadas.
 */
public interface DeudaSocioRepository extends JpaRepository<DeudaSocio, Long> {

    /**
     * Busca todas las deudas de un socio específico.
     *
     * @param socioId identificador del socio.
     * @return lista de deudas del socio.
     */
    List<DeudaSocio> findBySocioId(Long socioId);

    /**
     * Busca las deudas de un socio filtradas por estado.
     *
     * @param socioId identificador del socio.
     * @param estado  estado de la deuda (ACTIVA o PAGADA).
     * @return lista de deudas que coinciden con socio y estado.
     */
    List<DeudaSocio> findBySocioIdAndEstado(Long socioId, String estado);
}