package com.example.ms_fondo.repository;

import com.example.ms_fondo.model.DeudaSocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeudaSocioRepository extends JpaRepository<DeudaSocio, Long> {

    List<DeudaSocio> findBySocioId(Long socioId); // busca deudas por id de socio

    List<DeudaSocio> findBySocioIdAndEstado(Long socioId, String estado); // busca deudas por socio y estado
}