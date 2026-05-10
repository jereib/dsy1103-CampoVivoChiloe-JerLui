package com.example.ms_socios.repository;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocioRepository extends JpaRepository<Socio, Long> {

    List<Socio> findByEstado(Estado estado);
    boolean existsBySocioIgnoreCase(String socio);
}
