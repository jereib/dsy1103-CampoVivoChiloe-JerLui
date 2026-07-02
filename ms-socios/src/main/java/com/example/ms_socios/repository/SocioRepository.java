package com.example.ms_socios.repository;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Socio.
 * Spring Data genera automáticamente las implementaciones de los métodos.
 */
public interface SocioRepository extends JpaRepository<Socio, Long> {

    /**
     * Busca todas las familias socias que tengan un estado específico.
     *
     * @param estado estado por el que filtrar
     * @return lista de socios con ese estado
     */
    List<Socio> findByEstado(Estado estado);

    /**
     * Verifica si ya existe un socio con el mismo nombre (sin importar mayúsculas).
     *
     * @param socio nombre de la familia socia a verificar
     * @return true si ya existe un socio con ese nombre
     */
    boolean existsBySocioIgnoreCase(String socio);
}
