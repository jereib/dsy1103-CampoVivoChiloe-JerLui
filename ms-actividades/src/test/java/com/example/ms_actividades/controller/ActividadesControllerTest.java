package com.example.ms_actividades.controller;

import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.service.ActividadesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para el controlador de actividades.
 * Verifica que los endpoints respondan correctamente y manejen los errores esperados.
 */
@WebMvcTest(ActividadesController.class)
class ActividadesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ActividadesService actividadService;

    @Test
    void listarActividades_debeRetornarLista() throws Exception {
        List<ActividadesResponseDTO> actividades = List.of(
                new ActividadesResponseDTO("Taller", "Desc", "Sábado", "Los Jackson")
        );
        when(actividadService.listarActividades()).thenReturn(actividades);

        mockMvc.perform(get("/api/v1/actividades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(actividadService).listarActividades();
    }

    @Test
    void obtenerActividad_debeRetornarDTO() throws Exception {
        Long id = 1L;
        ActividadesResponseDTO dto = new ActividadesResponseDTO("Taller", "Desc", "Sábado", "Los Jackson");
        when(actividadService.obtenerActividad(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/actividades/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreActividad").value("Taller"));

        verify(actividadService).obtenerActividad(id);
    }

    @Test
    void obtenerActividad_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        when(actividadService.obtenerActividad(id)).thenThrow(new RuntimeException("Actividad no encontrada"));

        mockMvc.perform(get("/api/v1/actividades/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Actividad no encontrada"));

        verify(actividadService).obtenerActividad(id);
    }

    @Test
    void crearActividad_debeRetornar201() throws Exception {
        ActividadModel actividad = new ActividadModel(null, "Taller", "Desc", "Sábado", 1L);
        ActividadModel creada = new ActividadModel(1L, "Taller", "Desc", "Sábado", 1L);
        when(actividadService.crearActividad(any(ActividadModel.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v1/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actividad)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        verify(actividadService).crearActividad(any(ActividadModel.class));
    }

    @Test
    void actualizarActividad_debeRetornar200() throws Exception {
        Long id = 1L;
        ActividadModel actividad = new ActividadModel(id, "Taller", "Desc", "Sábado", 1L);
        when(actividadService.actualizarActividad(eq(id), any(ActividadModel.class))).thenReturn(actividad);

        mockMvc.perform(put("/api/v1/actividades/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actividad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreActividad").value("Taller"));

        verify(actividadService).actualizarActividad(eq(id), any(ActividadModel.class));
    }

    @Test
    void actualizarActividad_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        ActividadModel actividad = new ActividadModel(id, "Taller", "Desc", "Sábado", 1L);
        when(actividadService.actualizarActividad(eq(id), any(ActividadModel.class)))
                .thenThrow(new RuntimeException("Actividad no encontrada"));

        mockMvc.perform(put("/api/v1/actividades/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actividad)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Actividad no encontrada"));

        verify(actividadService).actualizarActividad(eq(id), any(ActividadModel.class));
    }

    @Test
    void eliminarActividad_debeRetornar200() throws Exception {
        Long id = 1L;
        doNothing().when(actividadService).eliminarActividad(id);

        mockMvc.perform(delete("/api/v1/actividades/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Actividad eliminada correctamente"));

        verify(actividadService).eliminarActividad(id);
    }

    @Test
    void eliminarActividad_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        doThrow(new RuntimeException("Actividad no encontrada")).when(actividadService).eliminarActividad(id);

        mockMvc.perform(delete("/api/v1/actividades/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Actividad no encontrada"));

        verify(actividadService).eliminarActividad(id);
    }

    @Test
    void actualizarParcial_debeRetornar200() throws Exception {
        Long id = 1L;
        ActividadModel actividad = new ActividadModel(id, "Taller", "Desc", "Sábado", 1L);
        Map<String, Object> campos = Map.of("nombreActividad", "Nuevo taller");
        when(actividadService.actualizarParcial(eq(id), anyMap())).thenReturn(actividad);

        mockMvc.perform(patch("/api/v1/actividades/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreActividad").value("Taller"));

        verify(actividadService).actualizarParcial(eq(id), anyMap());
    }

    @Test
    void actualizarParcial_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        Map<String, Object> campos = Map.of("nombreActividad", "Nuevo taller");
        when(actividadService.actualizarParcial(eq(id), anyMap()))
                .thenThrow(new RuntimeException("Actividad no encontrada con ID: " + id));

        mockMvc.perform(patch("/api/v1/actividades/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Actividad no encontrada con ID: 999"));

        verify(actividadService).actualizarParcial(eq(id), anyMap());
    }
}
