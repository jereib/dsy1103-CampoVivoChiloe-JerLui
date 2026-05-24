package com.example.ms_actividades.service;

import com.example.ms_actividades.client.SocioClient;
import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.dto.SocioDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.repository.ActividadesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActividadesService {

    @Autowired
    private ActividadesRepositorio actividadRepository;

    @Autowired
    private SocioClient socioClient;

    public ActividadesResponseDTO obtenerActividad(Long id){

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        // obtiene datos desde microservicio socios
        SocioDTO socio = socioClient.obtenerSocio(actividad.getSocioId());

        // crea respuesta
        ActividadesResponseDTO response = new ActividadesResponseDTO();

        response.setNombreActividad(actividad.getNombreActividad());
        response.setDescripcion(actividad.getDescripcion());
        response.setCalendario(actividad.getCalendario());

        // nombre de familia socia
        response.setSocio(socio.getSocio());

        return response;
    }

    public ActividadModel crearActividad(ActividadModel actividad){

        return actividadRepository.save(actividad);
    }

    public List<ActividadesResponseDTO> listarActividades(){

        List<ActividadModel> actividades = actividadRepository.findAll();

        List<ActividadesResponseDTO> respuesta = new ArrayList<>();

        for(ActividadModel actividad : actividades){

            SocioDTO socio = socioClient.obtenerSocio(actividad.getSocioId());

            ActividadesResponseDTO dto = new ActividadesResponseDTO();

            dto.setNombreActividad(actividad.getNombreActividad());
            dto.setDescripcion(actividad.getDescripcion());
            dto.setCalendario(actividad.getCalendario());


            dto.setSocio(socio.getSocio());

            respuesta.add(dto);
        }

        return respuesta;
    }

    public ActividadModel actualizarActividad(
            Long id,
            ActividadModel actividadActualizada){

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        actividad.setNombreActividad(
                actividadActualizada.getNombreActividad());

        actividad.setDescripcion(
                actividadActualizada.getDescripcion());

        actividad.setCalendario(
                actividadActualizada.getCalendario());

        actividad.setSocioId(
                actividadActualizada.getSocioId());

        return actividadRepository.save(actividad);
    }

    public void eliminarActividad(Long id){

        ActividadModel actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        actividadRepository.delete(actividad);
    }
}
