package com.example.ms_socios.repository;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Capa de acceso a datos, Spring Data genera las consultas automáticamente
public interface SocioRepository extends JpaRepository<Socio, Long> {

    // JPA convierte esto en: SELECT * FROM socio WHERE estado = ?
    List<Socio> findByEstado(Estado estado);
    // Verifica si ya existe un socio con el mismo nombre (sin importar mayúsculas)
    boolean existsBySocioIgnoreCase(String socio);
}
