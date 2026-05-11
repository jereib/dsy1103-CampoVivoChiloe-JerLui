package com.example.ms_recursos.service;

import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.repository.ReservaRecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservaRecursoService {

    @Autowired
    private ReservaRecursoRepository reservaRecursoRepository;

    private RestTemplate restTemplate = new RestTemplate();

    // URL del microservicio ms-fondo
    private String urlFondo = "http://localhost:8088/api/v1/deudas/validar/";

    // LISTAR
    public List<ReservaRecurso> listar() {
        return reservaRecursoRepository.findAll();
    }

    // BUSCAR
    public ReservaRecurso buscarPorId(Long id) {
        return reservaRecursoRepository.findById(id).orElse(null);
    }

    // GUARDAR (AQUÍ ESTÁ LA LÓGICA IMPORTANTE)
    public ReservaRecurso guardar(ReservaRecurso reserva) {

        // Consultar si el socio tiene deuda
        Boolean tieneDeuda = restTemplate.getForObject(
                urlFondo + reserva.getSocioId(),
                Boolean.class
        );

        // Si tiene deuda → no permitir reserva
        if (tieneDeuda != null && tieneDeuda) {
            throw new RuntimeException("El socio tiene deuda, no puede reservar recurso");
        }

        // Si no tiene deuda → guardar
        return reservaRecursoRepository.save(reserva);
    }

    // ELIMINAR
    public boolean eliminar(Long id) {
        ReservaRecurso r = buscarPorId(id);

        if (r == null) {
            return false;
        }

        reservaRecursoRepository.delete(r);
        return true;
    }
}