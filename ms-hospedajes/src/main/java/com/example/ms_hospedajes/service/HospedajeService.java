package com.example.ms_hospedajes.service;

import com.example.ms_hospedajes.client.HuespedClient;
import com.example.ms_hospedajes.client.SocioClient;
import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.repository.HospedajeRepositorio;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HospedajeService {

    private static final Logger logger = LoggerFactory.getLogger(HospedajeService.class);

    private final HuespedClient huespedClient;
    private final SocioClient socioClient;
    private final HospedajeRepositorio hospedajeRepositorio;

    public HospedajeService(HuespedClient huespedClient,
                            SocioClient socioClient,
                            HospedajeRepositorio hospedajeRepositorio) {
        this.huespedClient = huespedClient;
        this.socioClient = socioClient;
        this.hospedajeRepositorio = hospedajeRepositorio;
    }

    // Devuelve todos los hospedajes registrados
    public List<HospedajeModel> listar() {
        return hospedajeRepositorio.findAll();
    }

    // Obtiene un huésped llamando al ms-huespedes vía Feign
    public HuespedDTO obtenerHuesped(Long id){
        logger.debug("Consultando externamente al ms-huespedes por el ID: {}", id);
        try {
            HuespedDTO huesped = huespedClient.obtenerHuesped(id);
            if (huesped == null) {
                logger.warn("La consulta al ms-huespedes devolvió un objeto nulo para el ID: [{}]", id);
            }
            return huesped;
        } catch (Exception e) {
            logger.error("Error crítico de comunicación con ms-huespedes al buscar ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    // Obtiene un socio llamando al ms-socios vía Feign
    public SocioDTO obtenerSocio(Long id){
        logger.debug("Consultando externamente al ms-socios por el ID: {}", id);
        try {
            SocioDTO socio = socioClient.obtenerSocio(id);
            if (socio == null) {
                logger.warn("La consulta al ms-socios devolvió un objeto nulo para el ID: [{}]", id);
            }
            return socio;
        } catch (Exception e) {
            logger.error("Error crítico de comunicación con ms-socios al buscar ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    // Actualiza los IDs de un hospedaje existente
    public HospedajeModel actualizar(Long id, HospedajeModel datos) {
        HospedajeModel existente = hospedajeRepositorio.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        existente.setSocioId(datos.getSocioId());
        existente.setHuespedId(datos.getHuespedId());
        return hospedajeRepositorio.save(existente);
    }

    // Elimina un hospedaje si existe
    public boolean eliminar(Long id) {
        if (!hospedajeRepositorio.existsById(id)) {
            return false;
        }
        hospedajeRepositorio.deleteById(id);
        return true;
    }

    // Orquesta la creación: valida que existan socio y huésped, luego guarda la relación
    public String crearHospedaje(Long socioId, Long huespedId){
        logger.info("Iniciando proceso para orquestar hospedaje. SocioID: [{}], HuespedID: [{}]", socioId, huespedId);

        HuespedDTO huesped;
        try {
            huesped = huespedClient.obtenerHuesped(huespedId);
            if (huesped == null) {
                logger.warn("Orquestación fallida: ms-huespedes retornó vacío para el ID [{}]", huespedId);
                throw new RuntimeException("Huésped no encontrado");
            }
        } catch (Exception e) {
            logger.error("Fallo de comunicación al validar huésped con ID [{}]: {}", huespedId, e.getMessage());
            throw e;
        }

        SocioDTO socio;
        try {
            socio = socioClient.obtenerSocio(socioId);
            if (socio == null) {
                logger.warn("Orquestación fallida: ms-socios retornó vacío para el ID [{}]", socioId);
                throw new RuntimeException("Socio no encontrado");
            }
        } catch (Exception e) {
            logger.error("Fallo de comunicación al validar socio con ID [{}]: {}", socioId, e.getMessage());
            throw e;
        }

        HospedajeModel hospedaje = new HospedajeModel();
        hospedaje.setSocioId(socioId);
        hospedaje.setHuespedId(huespedId);

        try {
            HospedajeModel guardado = hospedajeRepositorio.save(hospedaje);

            logger.info("Hospedaje creado correctamente. ID Asignado: [{}]. Relación: Huésped [{}] -> Familia [{}]",
                    guardado.getId(), huesped.getNombreCompleto(), socio.getSocio());

        } catch (Exception e) {
            logger.error("Error al persistir la relación de hospedaje en la base de datos local: {}", e.getMessage());
            throw e;
        }

        return "Hospedaje creado para el huésped: "
                + huesped.getNombreCompleto()
                + " | Familia socia: "
                + socio.getSocio();
    }
}