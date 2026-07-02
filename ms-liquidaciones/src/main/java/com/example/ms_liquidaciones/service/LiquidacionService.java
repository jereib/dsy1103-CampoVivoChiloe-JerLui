package com.example.ms_liquidaciones.service;

import com.example.ms_liquidaciones.client.FondoClient;
import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.repository.LiquidacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiquidacionService {

    private static final Logger log = LoggerFactory.getLogger(LiquidacionService.class);

    private final FondoClient fondoClient; // para consultar deudas en ms-fondo
    private final LiquidacionRepository liquidacionRepository;

    public LiquidacionService(FondoClient fondoClient,
                              LiquidacionRepository liquidacionRepository) {
        this.fondoClient = fondoClient;
        this.liquidacionRepository = liquidacionRepository;
    }

    // Todas las liquidaciones
    public List<Liquidacion> listar() {
        return liquidacionRepository.findAll();
    }

    // Guardar una nueva liquidación
    public Liquidacion guardar(Liquidacion liquidacion) {
        return liquidacionRepository.save(liquidacion);
    }

    // Buscar por id, retorna null si no existe
    public Liquidacion buscarPorId(Long id) {
        return liquidacionRepository.findById(id).orElse(null);
    }

    // Actualizar todos los campos de una liquidación
    public Liquidacion actualizar(Long id, Liquidacion datos) {
        Liquidacion existente = liquidacionRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setSocioId(datos.getSocioId());
        existente.setIngresos(datos.getIngresos());
        existente.setDeuda(datos.getDeuda());
        existente.setTotal(datos.getTotal());
        return liquidacionRepository.save(existente);
    }

    // Eliminar por id, retorna false si no existe
    public boolean eliminar(Long id) {
        if (!liquidacionRepository.existsById(id)) {
            return false;
        }
        liquidacionRepository.deleteById(id);
        return true;
    }

    // Genera una liquidación calculando ingresos - deuda (consulta a ms-fondo)
    public Liquidacion generarLiquidacion(Long socioId) {
        log.info("Generando liquidación para socio ID: {}", socioId);

        Boolean tieneDeuda;
        try {
            tieneDeuda = fondoClient.tieneDeudaActiva(socioId);
            log.debug("Respuesta de ms-fondo para socio ID {}: tieneDeuda={}", socioId, tieneDeuda);
        } catch (Exception e) {
            log.error("Error al consultar ms-fondo para socio ID {}: {}", socioId, e.getMessage());
            throw new RuntimeException("No se pudo obtener el estado de deuda del socio.");
        }

        double ingresos = 200000;
        double deuda = tieneDeuda != null && tieneDeuda ? 50000 : 0;
        double total = ingresos - deuda;

        log.info("Liquidación calculada para socio ID {}: ingresos={}, deuda={}, total={}",
                socioId, ingresos, deuda, total);

        return new Liquidacion(socioId, ingresos, deuda, total);
    }
}