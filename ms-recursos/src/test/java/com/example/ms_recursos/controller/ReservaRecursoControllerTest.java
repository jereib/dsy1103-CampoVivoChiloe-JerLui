package com.example.ms_recursos.controller;

import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.service.ReservaRecursoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaRecursoController.class)
class ReservaRecursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservaRecursoService reservaRecursoService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void listar_debeRetornar200() throws Exception {
        when(reservaRecursoService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/recursos"))
                .andExpect(status().isOk());
    }

    @Test
    void buscar_debeRetornar200_cuandoExiste() throws Exception {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        r.setId(1L);
        when(reservaRecursoService.buscarPorId(1L)).thenReturn(r);

        mockMvc.perform(get("/api/v1/recursos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreRecurso").value("Tractor"));
    }

    @Test
    void buscar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(reservaRecursoService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/recursos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crear_debeRetornar201() throws Exception {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        ReservaRecurso guardada = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        guardada.setId(1L);
        when(reservaRecursoService.guardar(any())).thenReturn(guardada);

        mockMvc.perform(post("/api/v1/recursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isCreated());
    }

    @Test
    void crear_debeRetornar400_cuandoServiceLanzaExcepcion() throws Exception {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        when(reservaRecursoService.guardar(any())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/api/v1/recursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornar200() throws Exception {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        ReservaRecurso actualizada = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "CONFIRMADA");
        actualizada.setId(1L);
        when(reservaRecursoService.guardar(any())).thenReturn(actualizada);

        mockMvc.perform(put("/api/v1/recursos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_debeRetornar204() throws Exception {
        when(reservaRecursoService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/recursos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(reservaRecursoService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/recursos/99"))
                .andExpect(status().isNotFound());
    }
}
