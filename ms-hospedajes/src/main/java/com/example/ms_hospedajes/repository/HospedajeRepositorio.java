package com.example.ms_hospedajes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_hospedajes.model.HospedajeModel;
public interface HospedajeRepositorio extends JpaRepository<HospedajeModel, Long> {
}
