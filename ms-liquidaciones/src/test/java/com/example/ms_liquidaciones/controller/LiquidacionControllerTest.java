package com.example.ms_liquidaciones.controller;

import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.service.LiquidacionService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LiquidacionController.class)
class LiquidacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockitoBean
    private LiquidacionService liquidacionService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void listar_debeRetornar200() throws Exception {
        when(liquidacionService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/liquidaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void generar_debeRetornar200() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.generarLiquidacion(1L)).thenReturn(l);

        mockMvc.perform(get("/api/v1/liquidaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socioId").value(1))
                .andExpect(jsonPath("$.total").value(150000));
    }

    @Test
    void crear_debeRetornar201() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.guardar(any())).thenReturn(l);

        mockMvc.perform(post("/api/v1/liquidaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isCreated());
    }

    @Test
    void crear_debeRetornar400_cuandoServiceLanzaExcepcion() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.guardar(any())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/api/v1/liquidaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_debeRetornar200() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 0, 200000);
        when(liquidacionService.actualizar(eq(1L), any())).thenReturn(l);

        mockMvc.perform(put("/api/v1/liquidaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isOk());
    }

    @Test
    void actualizar_debeRetornar404_cuandoNoExiste() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 0, 200000);
        when(liquidacionService.actualizar(eq(1L), any())).thenReturn(null);

        mockMvc.perform(put("/api/v1/liquidaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_debeRetornar204() throws Exception {
        when(liquidacionService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/liquidaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(liquidacionService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/liquidaciones/99"))
                .andExpect(status().isNotFound());
    }
}
