package com.example.ms_socios.service;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.repository.SocioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SocioService {

    // Inicialización del Logger de SLF4J para la clase SocioService
    private static final Logger log = LoggerFactory.getLogger(SocioService.class);
    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository){
        this.socioRepository = socioRepository;
    }

    // Muestra las familias socias registradas
    public List<Socio> listarSocios(){
        log.info("Solicitando listado completo de familias socias");
        List<Socio> socios = socioRepository.findAll();
        log.debug("Se recuperaron {} registros de socios de la base de datos", socios.size());
        return socios;
    }

    // Busca por el estado de la familia socia
    public List<Socio> buscarPorEstado(Estado estado){
        log.info("Buscando familias socias con estado: [{}]", estado);
        List<Socio> socios = socioRepository.findByEstado(estado);
        log.debug("Se encontraron {} socios con el estado [{}]", socios.size(), estado);
        return socios;
    }

    // Busca por el id de la familia socia
    public Socio buscarPorId(Long id){
        log.debug("Buscando socio por ID: {}", id);
        Optional<Socio> socio = socioRepository.findById(id);

        if (socio.isEmpty()) {
            log.warn("No se encontró ninguna familia socia con el ID: [{}]", id);
            return null;
        }

        return socio.get();
    }

    // Guarda la familia socia
    public Socio guardarSocio(Socio socio){
        log.info("Iniciando registro de una nueva familia socia: [{}]", socio.getSocio());

        boolean existe = socioRepository.existsBySocioIgnoreCase(socio.getSocio());

        if(existe){
            log.warn("No se pudo registrar: Ya existe un socio con el nombre [{}]", socio.getSocio());
            return null;
        }

        try {
            Socio guardado = socioRepository.save(socio);
            log.info("Familia socia registrada exitosamente con ID: [{}]", guardado.getId());
            return guardado;
        } catch (Exception e) {
            log.error("Error crítico al intentar guardar el socio [{}]: {}", socio.getSocio(), e.getMessage());
            throw e;
        }
    }

    // Actualiza por id la familia socia
    public Socio actualizarPorId(Long id, Socio socioActualizado){
        log.info("Petición recibida para actualizar (PUT) socio con ID: [{}]", id);

        Optional<Socio> socioExistente = socioRepository.findById(id);

        if(socioExistente.isEmpty()){
            log.warn("Fallo de actualización completa: El socio con ID [{}] no existe", id);
            return null;
        }

        Socio socio = socioExistente.get();

        socio.setSocio(socioActualizado.getSocio());
        socio.setPredio(socioActualizado.getPredio());
        socio.setCapacidad(socioActualizado.getCapacidad());
        socio.setEstado(socioActualizado.getEstado());

        try {
            Socio actualizado = socioRepository.save(socio);
            log.info("Socio con ID: [{}] actualizado por completo exitosamente", id);
            return actualizado;
        } catch (Exception e) {
            log.error("Error al actualizar (PUT) el socio con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    // Elimina a la familia socia por id
    public boolean eliminarSocio(Long id){
        log.info("Petición recibida para eliminar socio con ID: [{}]", id);

        Optional<Socio> socio = socioRepository.findById(id);

        if(socio.isEmpty()){
            log.warn("Fallo de eliminación: El socio con ID [{}] no existe", id);
            return false;
        }

        try {
            socioRepository.deleteById(id);
            log.info("Socio con ID: [{}] ha sido eliminado correctamente", id);
            return true;
        } catch (Exception e) {
            log.error("Error al intentar eliminar el socio con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    // Actualiza parcialmente a la familia socia
    public Socio actualizarParcial(Long id, Map<String, Object> campos) {
        log.info("Petición recibida para actualización parcial (PATCH) del socio con ID: [{}]", id);

        Optional<Socio> socioExistenteOpt = socioRepository.findById(id);
        if (socioExistenteOpt.isEmpty()) {
            log.warn("Fallo de actualización parcial: El socio con ID [{}] no existe", id);
            return null;
        }

        Socio socioActual = socioExistenteOpt.get();

        try {
            campos.forEach((clave, valor) -> {
                log.debug("Aplicando cambio parcial - Campo: [{}], Nuevo Valor: [{}]", clave, valor);
                switch (clave) {
                    case "socio":
                        socioActual.setSocio((String) valor);
                        break;
                    case "predio":
                        socioActual.setPredio((String) valor);
                        break;
                    case "capacidad":
                        socioActual.setCapacidad((Integer) valor);
                        break;
                    case "estado":
                        socioActual.setEstado(Estado.valueOf(valor.toString().toUpperCase()));
                        break;
                    default:
                        log.warn("Se intentó modificar un campo inexistente o no permitido: [{}] en ID [{}]", clave, id);
                        break;
                }
            });

            Socio guardado = socioRepository.save(socioActual);
            log.info("Socio con ID: [{}] actualizado parcialmente de forma exitosa", id);
            return guardado;

        } catch (IllegalArgumentException e) {
            log.error("Error de validación en actualización parcial para ID [{}]: Estado inválido provisto", id);
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado en actualización parcial para ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }
}