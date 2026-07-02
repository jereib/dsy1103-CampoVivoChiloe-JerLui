package com.example.ms_actividades.service;

import com.example.ms_actividades.client.SocioClient;
import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.dto.SocioDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.repository.ActividadesRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servicio que contiene la lógica de negocio de las actividades.
 * Se encarga de coordinar el repositorio JPA y el cliente Feign
 * para armar la respuesta con los datos del socio.
 */
@Service
public class ActividadesService {

    private static final Logger log = LoggerFactory.getLogger(ActividadesService.class);

    @Autowired
    private ActividadesRepositorio actividadRepository;

    @Autowired
    private SocioClient socioClient; // para llamar al ms-socios

    /**
     * Obtiene una actividad por su id y le asigna el nombre del socio.
     *
     * @param id identificador de la actividad.
     * @return DTO con los datos de la actividad y el nombre del socio.
     * @throws RuntimeException si la actividad no existe o falla la comunicación con ms-socios.
     */
    public ActividadesResponseDTO obtenerActividad(Long id){
        log.info("Petición para obtener detalles de la actividad con ID: [{}]", id);

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo al obtener actividad: ID [{}] no existe en la base de datos", id);
                    return new RuntimeException("Actividad no encontrada");
                });

        SocioDTO socio;
        try {
            log.debug("Consultando externamente al ms-socios para obtener la familia con ID: [{}]", actividad.getSocioId());
            socio = socioClient.obtenerSocio(actividad.getSocioId());
        } catch (Exception e) {
            log.error("Error de comunicación con ms-socios para el socioId [{}]: {}", actividad.getSocioId(), e.getMessage());
            throw new RuntimeException("Error al recuperar los datos del socio vinculado a la actividad");
        }

        // crea respuesta
        ActividadesResponseDTO response = new ActividadesResponseDTO();
        response.setNombreActividad(actividad.getNombreActividad());
        response.setDescripcion(actividad.getDescripcion());
        response.setCalendario(actividad.getCalendario());

        // asignamos el nombre del socio si existe
        if (socio != null) {
            response.setSocio(socio.getSocio());
        }

        log.info("Estructura de respuesta (DTO) generada exitosamente para la actividad ID: [{}]", id);
        return response;
    }

    /**
     * Guarda una nueva actividad en la base de datos.
     *
     * @param actividad datos de la actividad a crear.
     * @return la actividad creada con su id generado.
     */
    public ActividadModel crearActividad(ActividadModel actividad){
        log.info("Iniciando creación de una nueva actividad: [{}]", actividad.getNombreActividad());
        try {
            ActividadModel nuevaActividad = actividadRepository.save(actividad);
            log.info("Actividad [{}] creada exitosamente con ID asignado: [{}]", nuevaActividad.getNombreActividad(), nuevaActividad.getId());
            return nuevaActividad;
        } catch (Exception e) {
            log.error("Error crítico al intentar persistir la actividad [{}]: {}", actividad.getNombreActividad(), e.getMessage());
            throw e;
        }
    }

    /**
     * Lista todas las actividades con el nombre del socio en cada una.
     * Si falla la comunicación con ms-socios para alguna actividad,
     * esa actividad se omite pero el resto se sigue procesando.
     *
     * @return lista de DTOs con los datos de cada actividad y su socio.
     */
    public List<ActividadesResponseDTO> listarActividades(){
        log.info("Solicitando listado completo de actividades");
        List<ActividadModel> actividades = actividadRepository.findAll();
        log.debug("Se recuperaron {} registros de actividades desde la base de datos", actividades.size());

        List<ActividadesResponseDTO> respuesta = new ArrayList<>();

        for(ActividadModel actividad : actividades){
            try {
                log.debug("Buscando datos del socio ID: [{}] para la actividad: [{}]", actividad.getSocioId(), actividad.getNombreActividad());
                SocioDTO socio = socioClient.obtenerSocio(actividad.getSocioId());

                ActividadesResponseDTO dto = new ActividadesResponseDTO();
                dto.setNombreActividad(actividad.getNombreActividad());
                dto.setDescripcion(actividad.getDescripcion());
                dto.setCalendario(actividad.getCalendario());

                if (socio != null) {
                    dto.setSocio(socio.getSocio());
                }

                respuesta.add(dto);
            } catch (Exception e) {
                // Logueamos el error pero permitimos que el ciclo continúe para no romper el listado entero por un solo socio fallido
                log.error("No se pudieron fusionar los datos del socio ID [{}] en la actividad [{}]: {}",
                        actividad.getSocioId(), actividad.getNombreActividad(), e.getMessage());
            }
        }

        log.info("Listado procesado. Se devuelven {} actividades formateadas", respuesta.size());
        return respuesta;
    }

    /**
     * Reemplaza todos los campos de una actividad existente.
     *
     * @param id                  identificador de la actividad.
     * @param actividadActualizada datos nuevos de la actividad.
     * @return la actividad actualizada.
     * @throws RuntimeException si la actividad no existe.
     */
    public ActividadModel actualizarActividad(Long id, ActividadModel actividadActualizada){
        log.info("Petición recibida para actualizar (PUT) la actividad con ID: [{}]", id);

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo de actualización completa: La actividad con ID [{}] no existe", id);
                    return new RuntimeException("Actividad no encontrada");
                });

        actividad.setNombreActividad(actividadActualizada.getNombreActividad());
        actividad.setDescripcion(actividadActualizada.getDescripcion());
        actividad.setCalendario(actividadActualizada.getCalendario());
        actividad.setSocioId(actividadActualizada.getSocioId());

        try {
            ActividadModel actualizada = actividadRepository.save(actividad);
            log.info("Actividad con ID: [{}] actualizada por completo exitosamente", id);
            return actualizada;
        } catch (Exception e) {
            log.error("Error al actualizar (PUT) la actividad con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * Elimina una actividad de la base de datos.
     *
     * @param id identificador de la actividad a eliminar.
     * @throws RuntimeException si la actividad no existe.
     */
    public void eliminarActividad(Long id){
        log.info("Petición recibida para eliminar la actividad con ID: [{}]", id);

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo de eliminación: La actividad con ID [{}] no existe", id);
                    return new RuntimeException("Actividad no encontrada");
                });

        try {
            actividadRepository.delete(actividad);
            log.info("Actividad con ID: [{}] eliminada correctamente", id);
        } catch (Exception e) {
            log.error("Error al intentar eliminar la actividad con ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * Actualiza solo los campos indicados en el mapa (PATCH).
     * Los campos válidos son: nombreActividad, descripcion, calendario y socioId.
     *
     * @param id     identificador de la actividad.
     * @param campos mapa con los nombres de campo y sus nuevos valores.
     * @return la actividad con los cambios aplicados.
     * @throws RuntimeException si la actividad no existe.
     */
    public ActividadModel actualizarParcial(Long id, Map<String, Object> campos) {
        log.info("Petición recibida para actualización parcial (PATCH) de la actividad con ID: [{}]", id);

        ActividadModel actividadActual = actividadRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Fallo de actualización parcial: La actividad con ID [{}] no existe", id);
                    return new RuntimeException("Actividad no encontrada con ID: " + id);
                });

        try {
            campos.forEach((clave, valor) -> {
                log.debug("Aplicando cambio parcial - Campo: [{}], Nuevo Valor: [{}]", clave, valor);
                switch (clave) {
                    case "nombreActividad":
                        actividadActual.setNombreActividad((String) valor);
                        break;
                    case "descripcion":
                        actividadActual.setDescripcion((String) valor);
                        break;
                    case "calendario":
                        actividadActual.setCalendario((String) valor);
                        break;
                    case "socioId":
                        if (valor != null) {
                            actividadActual.setSocioId(Long.valueOf(valor.toString()));
                        } else {
                            actividadActual.setSocioId(null);
                        }
                        break;
                    default:
                        log.warn("Se intentó modificar un campo inexistente o restringido: [{}] en la actividad ID [{}]", clave, id);
                        break;
                }
            });

            ActividadModel guardada = actividadRepository.save(actividadActual);
            log.info("Actividad con ID: [{}] actualizada parcialmente con éxito", id);
            return guardada;

        } catch (Exception e) {
            log.error("Error inesperado en la actualización parcial (PATCH) para la actividad ID [{}]: {}", id, e.getMessage());
            throw e;
        }
    }
}