package com.example.ms_huespedes.service;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.repository.HuespedRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HuespedService {

    // Inicialización del Logger de SLF4J para la clase HuespedService
    private static final Logger log = LoggerFactory.getLogger(HuespedService.class);
    private final HuespedRepositorio huespedRepositorio;

    public HuespedService(HuespedRepositorio huespedRepositorio) {
        this.huespedRepositorio = huespedRepositorio;
    }

    public List<Huesped> listarHuespedes(){
        log.info("Solicitando listado completo de todos los huéspedes registrados");
        List<Huesped> huespedes = huespedRepositorio.findAll();
        log.debug("Se recuperaron {} huéspedes de la base de datos", huespedes.size());
        return huespedes;
    }

    public Huesped buscarPorId(Long id){
        log.debug("Buscando huésped por ID: {}", id);
        Optional<Huesped> huesped = huespedRepositorio.findById(id);

        if (huesped.isEmpty()) {
            log.warn("No se encontró ningún huésped con el ID: [{}]", id);
            return null;
        }

        return huesped.get();
    }

    public Huesped guardarHuesped(Huesped huesped){
        log.info("Iniciando el registro de un nuevo huésped: [{}]", huesped.getNombreCompleto());

        boolean existe = huespedRepositorio.existsByNombreCompletoIgnoreCase(huesped.getNombreCompleto());

        if(existe) {
            log.warn("No se pudo registrar: Ya existe un huésped con el nombre [{}]", huesped.getNombreCompleto());
            return null;
        }

        try {
            Huesped guardado = huespedRepositorio.save(huesped);
            log.info("Huésped registrado exitosamente con ID: [{}] y RUT: [{}]", guardado.getId(), guardado.getRut());
            return guardado;
        } catch (Exception e) {
            log.error("Error crítico al intentar guardar el huésped [{}]: {}", huesped.getNombreCompleto(), e.getMessage());
            throw e;
        }
    }

    public Huesped actualizarPorId(Long id, Huesped huespedActualizado){
        log.info("Petición recibida para actualizar (PUT) huésped con ID: [{}]", id);

        Optional<Huesped> huespedExistente = huespedRepositorio.findById(id);

        if(huespedExistente.isEmpty()){
            log.warn("Fallo de actualización completa: El huésped con ID [{}] no existe", id);
            return null;
        }

        Huesped huesped = huespedExistente.get();

        huesped.setNombreCompleto(huespedActualizado.getNombreCompleto());
        huesped.setRut(huespedActualizado.getRut());
        huesped.setEdad(huespedActualizado.getEdad());
        huesped.setPerfil(huespedActualizado.getPerfil());
        huesped.setHistorial(huespedActualizado.getHistorial());
        huesped.setCorreo(huespedActualizado.getCorreo());

        try {
            Huesped actualizado = huespedRepositorio.save(huesped);
            log.info("Huésped con ID: [{}] actualizado por completo exitosamente", id);
            return actualizado;
        } catch (Exception e) {
            log.error("Error al actualizar (PUT) el huésped con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    public boolean eliminarHuesped(Long id){
        log.info("Petición recibida para eliminar huésped con ID: [{}]", id);

        Optional<Huesped> huesped = huespedRepositorio.findById(id);

        if(huesped.isEmpty()){
            log.warn("Fallo de eliminación: El huésped con ID [{}] no existe", id);
            return false;
        }

        try {
            huespedRepositorio.deleteById(id);
            log.info("Huésped con ID: [{}] ha sido eliminado correctamente de la base de datos", id);
            return true;
        } catch (Exception e) {
            log.error("Error al intentar eliminar el huésped con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    public Huesped actualizarParcial(Long id, Map<String, Object> campos) {
        log.info("Petición recibida para actualización parcial (PATCH) del huésped con ID: [{}]", id);

        Optional<Huesped> huespedOpt = huespedRepositorio.findById(id);
        if (huespedOpt.isEmpty()) {
            log.warn("Fallo de actualización parcial: El huésped con ID [{}] no existe", id);
            return null;
        }

        Huesped huespedActual = huespedOpt.get();

        try {
            campos.forEach((clave, valor) -> {
                log.debug("Aplicando cambio parcial - Campo: [{}], Nuevo Valor: [{}]", clave, valor);
                switch (clave) {
                    case "nombreCompleto":
                        huespedActual.setNombreCompleto((String) valor);
                        break;
                    case "rut":
                        huespedActual.setRut((String) valor);
                        break;
                    case "edad":
                        huespedActual.setEdad((Integer) valor);
                        break;
                    case "perfil":
                        huespedActual.setPerfil((String) valor);
                        break;
                    case "historial":
                        huespedActual.setHistorial((String) valor);
                        break;
                    case "correo":
                        huespedActual.setCorreo((String) valor);
                        break;
                    default:
                        log.warn("Se intentó modificar un campo no permitido o inexistente: [{}] en ID [{}]", clave, id);
                        break;
                }
            });

            Huesped guardado = huespedRepositorio.save(huespedActual);
            log.info("Huésped con ID: [{}] actualizado parcialmente de forma exitosa", id);
            return guardado;

        } catch (Exception e) {
            log.error("Error inesperado en la actualización parcial (PATCH) para el ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }
}