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

/**
 * Pruebas unitarias del controlador LiquidacionController.
 * Verifica que los endpoints respondan correctamente según cada escenario.
 */
@WebMvcTest(LiquidacionController.class)
class LiquidacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockitoBean
    private LiquidacionService liquidacionService;

    /**
     * Prepara el ObjectMapper antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Verifica que listar liquidaciones retorne 200 OK.
     */
    @Test
    void listar_debeRetornar200() throws Exception {
        when(liquidacionService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/liquidaciones"))
                .andExpect(status().isOk());
    }

    /**
     * Verifica que generar liquidación retorne 200 con los datos correctos.
     */
    @Test
    void generar_debeRetornar200() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.generarLiquidacion(1L)).thenReturn(l);

        mockMvc.perform(get("/api/v1/liquidaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socioId").value(1))
                .andExpect(jsonPath("$.total").value(150000));
    }

    /**
     * Verifica que crear una liquidación retorne 201 Created.
     */
    @Test
    void crear_debeRetornar201() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.guardar(any())).thenReturn(l);

        mockMvc.perform(post("/api/v1/liquidaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isCreated());
    }

    /**
     * Verifica que crear retorne 400 cuando el servicio lanza una excepción.
     */
    @Test
    void crear_debeRetornar400_cuandoServiceLanzaExcepcion() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionService.guardar(any())).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(post("/api/v1/liquidaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifica que actualizar una liquidación existente retorne 200 OK.
     */
    @Test
    void actualizar_debeRetornar200() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 0, 200000);
        when(liquidacionService.actualizar(eq(1L), any())).thenReturn(l);

        mockMvc.perform(put("/api/v1/liquidaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isOk());
    }

    /**
     * Verifica que actualizar retorne 404 cuando la liquidación no existe.
     */
    @Test
    void actualizar_debeRetornar404_cuandoNoExiste() throws Exception {
        Liquidacion l = new Liquidacion(1L, 200000, 0, 200000);
        when(liquidacionService.actualizar(eq(1L), any())).thenReturn(null);

        mockMvc.perform(put("/api/v1/liquidaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(l)))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifica que eliminar una liquidación existente retorne 204 No Content.
     */
    @Test
    void eliminar_debeRetornar204() throws Exception {
        when(liquidacionService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/liquidaciones/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * Verifica que eliminar retorne 404 cuando la liquidación no existe.
     */
    @Test
    void eliminar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(liquidacionService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/liquidaciones/99"))
                .andExpect(status().isNotFound());
    }
}
