package com.example.ms_liquidaciones.repository;

import com.example.ms_liquidaciones.model.Liquidacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {
    // métodos CRUD básicos, no necesitamos consultas personalizadas
}
