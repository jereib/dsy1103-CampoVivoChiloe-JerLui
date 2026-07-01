package com.example.ms_recursos.service;

import com.example.ms_recursos.client.FondoClient;
import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.repository.ReservaRecursoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaRecursoService {

    private static final Logger log = LoggerFactory.getLogger(ReservaRecursoService.class);

    private final ReservaRecursoRepository reservaRecursoRepository;
    private final FondoClient fondoClient;

    public ReservaRecursoService(ReservaRecursoRepository reservaRecursoRepository,
                                  FondoClient fondoClient) {
        this.reservaRecursoRepository = reservaRecursoRepository;
        this.fondoClient = fondoClient;
    }

    public List<ReservaRecurso> listar() {
        log.info("Listando todos los recursos registrados");
        List<ReservaRecurso> recursos = reservaRecursoRepository.findAll();
        log.debug("Se recuperaron {} recursos de la base de datos", recursos.size());
        return recursos;
    }

    public ReservaRecurso buscarPorId(Long id) {
        log.debug("Buscando recurso por ID: {}", id);
        return reservaRecursoRepository.findById(id).orElse(null);
    }

    public ReservaRecurso guardar(ReservaRecurso reserva) {
        log.info("Validando deuda del socio ID: {} antes de reservar recurso", reserva.getSocioId());

        Boolean tieneDeuda;
        try {
            tieneDeuda = fondoClient.tieneDeudaActiva(reserva.getSocioId());
            log.debug("Respuesta de ms-fondo para socio ID {}: tieneDeuda={}", reserva.getSocioId(), tieneDeuda);
        } catch (Exception e) {
            log.error("Error al consultar ms-fondo para socio ID {}: {}", reserva.getSocioId(), e.getMessage());
            throw new RuntimeException("No se pudo validar la deuda del socio. Intente más tarde.");
        }

        if (tieneDeuda != null && tieneDeuda) {
            log.warn("Reserva denegada: el socio ID {} tiene deuda activa", reserva.getSocioId());
            throw new RuntimeException("El socio tiene deuda, no puede reservar recurso");
        }

        log.info("Socio ID {} sin deuda. Procediendo a guardar la reserva del recurso: {}",
                reserva.getSocioId(), reserva.getNombreRecurso());
        return reservaRecursoRepository.save(reserva);
    }

    public boolean eliminar(Long id) {
        log.info("Eliminando recurso con ID: {}", id);
        ReservaRecurso r = buscarPorId(id);

        if (r == null) {
            log.warn("No se encontró recurso con ID: {} para eliminar", id);
            return false;
        }

        reservaRecursoRepository.delete(r);
        log.info("Recurso con ID: {} eliminado correctamente", id);
        return true;
    }
}