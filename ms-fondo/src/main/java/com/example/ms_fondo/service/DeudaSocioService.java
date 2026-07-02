package com.example.ms_fondo.service;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.repository.DeudaSocioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
/**
 * Servicio que contiene la lógica de negocio para gestionar las deudas de socios.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
public class DeudaSocioService {

    private static final Logger log = LoggerFactory.getLogger(DeudaSocioService.class);

    @Autowired
    private DeudaSocioRepository deudaSocioRepository;

    /**
     * Obtiene todas las deudas registradas en el sistema.
     *
     * @return lista de todas las deudas.
     */
    public List<DeudaSocio> listar() {
        log.info("Listando todas las deudas registradas");
        return deudaSocioRepository.findAll();
    }

    /**
     * Busca una deuda por su identificador.
     *
     * @param id identificador de la deuda.
     * @return la deuda encontrada o null si no existe.
     */
    public DeudaSocio buscarPorId(Long id) {
        log.debug("Buscando deuda por ID: {}", id);
        return deudaSocioRepository.findById(id).orElse(null);
    }

    /**
     * Guarda una nueva deuda en la base de datos.
     *
     * @param deudaSocio datos de la deuda a guardar.
     * @return la deuda guardada.
     */
    public DeudaSocio guardar(DeudaSocio deudaSocio) {
        log.info("Guardando nueva deuda para socio ID: {}", deudaSocio.getSocioId());
        return deudaSocioRepository.save(deudaSocio);
    }

    /**
     * Actualiza completamente una deuda existente.
     *
     * @param id         identificador de la deuda a actualizar.
     * @param deudaSocio deuda con los nuevos datos.
     * @return la deuda actualizada o null si no se encontró.
     */
    public DeudaSocio actualizar(Long id, DeudaSocio deudaSocio) {
        log.info("Actualizando deuda ID: {}", id);
        DeudaSocio deudaExistente = buscarPorId(id);

        if (deudaExistente == null) {
            log.warn("Deuda ID: {} no encontrada para actualizar", id);
            return null;
        }

        deudaExistente.setSocioId(deudaSocio.getSocioId());
        deudaExistente.setMonto(deudaSocio.getMonto());
        deudaExistente.setEstado(deudaSocio.getEstado());

        return deudaSocioRepository.save(deudaExistente);
    }

    /**
     * Elimina una deuda del sistema.
     *
     * @param id identificador de la deuda a eliminar.
     * @return true si se eliminó correctamente, false si no existe.
     */
    public boolean eliminar(Long id) {
        log.info("Eliminando deuda ID: {}", id);
        DeudaSocio deudaExistente = buscarPorId(id);

        if (deudaExistente == null) {
            log.warn("Deuda ID: {} no encontrada para eliminar", id);
            return false;
        }

        deudaSocioRepository.delete(deudaExistente);
        log.info("Deuda ID: {} eliminada correctamente", id);
        return true;
    }

    /**
     * Obtiene todas las deudas asociadas a un socio.
     *
     * @param socioId identificador del socio.
     * @return lista de deudas del socio.
     */
    public List<DeudaSocio> buscarPorSocio(Long socioId) {
        log.debug("Buscando deudas del socio ID: {}", socioId);
        return deudaSocioRepository.findBySocioId(socioId);
    }

    /**
     * Verifica si un socio tiene al menos una deuda activa.
     *
     * @param socioId identificador del socio.
     * @return true si tiene deuda activa, false en caso contrario.
     */
    public boolean tieneDeudaActiva(Long socioId) {
        log.debug("Verificando deuda activa para socio ID: {}", socioId);
        List<DeudaSocio> deudasActivas =
                deudaSocioRepository.findBySocioIdAndEstado(socioId, "ACTIVA");

        boolean activa = !deudasActivas.isEmpty();
        log.debug("Socio ID {} tiene deuda activa: {}", socioId, activa);
        return activa;
    }

    /**
     * Actualiza solo los campos indicados de una deuda existente.
     *
     * @param id     identificador de la deuda.
     * @param campos mapa con los campos y valores a actualizar.
     * @return la deuda actualizada o null si no se encontró.
     */
    public DeudaSocio actualizarParcial(Long id, Map<String, Object> campos) {
        log.info("Actualizando parcialmente deuda ID: {}", id);
        DeudaSocio deudaExistente = buscarPorId(id);

        if (deudaExistente == null) {
            log.warn("Deuda ID: {} no encontrada para actualización parcial", id);
            return null;
        }

        campos.forEach((clave, valor) -> {
            switch (clave) {
                case "socioId":
                    if (valor != null) {
                        deudaExistente.setSocioId(Long.valueOf(valor.toString()));
                    } else {
                        deudaExistente.setSocioId(null);
                    }
                    break;
                case "monto":
                    if (valor != null) {
                        deudaExistente.setMonto(Double.parseDouble(valor.toString()));
                    }
                    break;
                case "estado":
                    if (valor != null) {
                        deudaExistente.setEstado(valor.toString().toUpperCase());
                    }
                    break;
                default:
                    break;
            }
        });

        return deudaSocioRepository.save(deudaExistente);
    }
}