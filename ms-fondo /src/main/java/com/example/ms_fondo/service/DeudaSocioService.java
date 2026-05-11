package com.example.ms_fondo.service;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.repository.DeudaSocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaSocioService {

    @Autowired
    private DeudaSocioRepository deudaSocioRepository;

    // Lista todas las deudas registradas
    public List<DeudaSocio> listar() {
        return deudaSocioRepository.findAll();
    }

    // Busca una deuda por su id
    public DeudaSocio buscarPorId(Long id) {
        return deudaSocioRepository.findById(id).orElse(null);
    }

    // Guarda una nueva deuda
    public DeudaSocio guardar(DeudaSocio deudaSocio) {
        return deudaSocioRepository.save(deudaSocio);
    }

    // Actualiza una deuda existente
    public DeudaSocio actualizar(Long id, DeudaSocio deudaSocio) {
        DeudaSocio deudaExistente = buscarPorId(id);

        if (deudaExistente == null) {
            return null;
        }

        deudaExistente.setSocioId(deudaSocio.getSocioId());
        deudaExistente.setMonto(deudaSocio.getMonto());
        deudaExistente.setEstado(deudaSocio.getEstado());

        return deudaSocioRepository.save(deudaExistente);
    }

    // Elimina una deuda por id
    public boolean eliminar(Long id) {
        DeudaSocio deudaExistente = buscarPorId(id);

        if (deudaExistente == null) {
            return false;
        }

        deudaSocioRepository.delete(deudaExistente);
        return true;
    }

    // Busca todas las deudas de un socio
    public List<DeudaSocio> buscarPorSocio(Long socioId) {
        return deudaSocioRepository.findBySocioId(socioId);
    }

    // Revisa si un socio tiene deuda activa
    public boolean tieneDeudaActiva(Long socioId) {
        List<DeudaSocio> deudasActivas =
                deudaSocioRepository.findBySocioIdAndEstado(socioId, "ACTIVA");

        return !deudasActivas.isEmpty();
    }
}