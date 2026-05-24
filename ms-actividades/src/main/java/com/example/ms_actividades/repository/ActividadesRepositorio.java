package com.example.ms_actividades.repository;

import com.example.ms_actividades.model.ActividadModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActividadesRepositorio extends JpaRepository<ActividadModel, Long> {
}
