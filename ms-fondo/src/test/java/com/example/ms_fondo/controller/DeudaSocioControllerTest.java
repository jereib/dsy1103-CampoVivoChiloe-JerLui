package com.example.ms_fondo.controller;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.service.DeudaSocioService;
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
 * Pruebas unitarias del controlador DeudaSocioController.
 * Simula peticiones HTTP y verifica las respuestas del controlador.
 */
@WebMvcTest(DeudaSocioController.class)
class DeudaSocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private DeudaSocioService deudaSocioService;

    /**
     * Verifica que listar todas las deudas retorna una lista con código 200.
     */
    @Test
    void listar_debeRetornarLista() throws Exception {
        List<DeudaSocio> deudas = List.of(
                new DeudaSocio(1L, 30000, "ACTIVA"),
                new DeudaSocio(1L, 15000, "PAGADA")
        );
        when(deudaSocioService.listar()).thenReturn(deudas);

        mockMvc.perform(get("/api/v1/deudas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(deudaSocioService).listar();
    }

    /**
     * Verifica que buscar una deuda por id retorna la deuda con código 200.
     */
    @Test
    void buscarPorId_debeRetornarDeuda() throws Exception {
        Long id = 1L;
        DeudaSocio deuda = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioService.buscarPorId(id)).thenReturn(deuda);

        mockMvc.perform(get("/api/v1/deudas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(30000));

        verify(deudaSocioService).buscarPorId(id);
    }

    /**
     * Verifica que buscar una deuda inexistente retorna código 404.
     */
    @Test
    void buscarPorId_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        when(deudaSocioService.buscarPorId(id)).thenReturn(null);

        mockMvc.perform(get("/api/v1/deudas/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Deuda no encontrada"));

        verify(deudaSocioService).buscarPorId(id);
    }

    /**
     * Verifica que crear una deuda retorna código 201.
     */
    @Test
    void crear_debeRetornar201() throws Exception {
        DeudaSocio deuda = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioService.guardar(any(DeudaSocio.class))).thenReturn(deuda);

        mockMvc.perform(post("/api/v1/deudas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deuda)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.monto").value(30000));

        verify(deudaSocioService).guardar(any(DeudaSocio.class));
    }

    /**
     * Verifica que actualizar una deuda existente retorna código 200.
     */
    @Test
    void actualizar_debeRetornar200() throws Exception {
        Long id = 1L;
        DeudaSocio deuda = new DeudaSocio(2L, 50000, "PAGADA");
        when(deudaSocioService.actualizar(eq(id), any(DeudaSocio.class))).thenReturn(deuda);

        mockMvc.perform(put("/api/v1/deudas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deuda)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(50000));

        verify(deudaSocioService).actualizar(eq(id), any(DeudaSocio.class));
    }

    /**
     * Verifica que actualizar una deuda inexistente retorna código 404.
     */
    @Test
    void actualizar_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        DeudaSocio deuda = new DeudaSocio(1L, 50000, "PAGADA");
        when(deudaSocioService.actualizar(eq(id), any(DeudaSocio.class))).thenReturn(null);

        mockMvc.perform(put("/api/v1/deudas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deuda)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Deuda no encontrada"));

        verify(deudaSocioService).actualizar(eq(id), any(DeudaSocio.class));
    }

    /**
     * Verifica que eliminar una deuda existente retorna código 204.
     */
    @Test
    void eliminar_debeRetornar204() throws Exception {
        Long id = 1L;
        when(deudaSocioService.eliminar(id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/deudas/{id}", id))
                .andExpect(status().isNoContent());

        verify(deudaSocioService).eliminar(id);
    }

    /**
     * Verifica que eliminar una deuda inexistente retorna código 404.
     */
    @Test
    void eliminar_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        when(deudaSocioService.eliminar(id)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/deudas/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Deuda no encontrada"));

        verify(deudaSocioService).eliminar(id);
    }

    /**
     * Verifica que buscar deudas por socio retorna una lista con código 200.
     */
    @Test
    void buscarPorSocio_debeRetornarLista() throws Exception {
        Long socioId = 1L;
        List<DeudaSocio> deudas = List.of(
                new DeudaSocio(socioId, 30000, "ACTIVA"),
                new DeudaSocio(socioId, 15000, "PAGADA")
        );
        when(deudaSocioService.buscarPorSocio(socioId)).thenReturn(deudas);

        mockMvc.perform(get("/api/v1/deudas/socio/{socioId}", socioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

        verify(deudaSocioService).buscarPorSocio(socioId);
    }

    /**
     * Verifica que validar deuda activa retorna un booleano con código 200.
     */
    @Test
    void tieneDeuda_debeRetornarBoolean() throws Exception {
        Long socioId = 1L;
        when(deudaSocioService.tieneDeudaActiva(socioId)).thenReturn(true);

        mockMvc.perform(get("/api/v1/deudas/validar/{socioId}", socioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        verify(deudaSocioService).tieneDeudaActiva(socioId);
    }

    /**
     * Verifica que actualizar parcialmente una deuda retorna código 200.
     */
    @Test
    void actualizarParcial_debeRetornar200() throws Exception {
        Long id = 1L;
        DeudaSocio deuda = new DeudaSocio(1L, 50000, "ACTIVA");
        Map<String, Object> campos = Map.of("monto", 50000.0);
        when(deudaSocioService.actualizarParcial(eq(id), anyMap())).thenReturn(deuda);

        mockMvc.perform(patch("/api/v1/deudas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(50000));

        verify(deudaSocioService).actualizarParcial(eq(id), anyMap());
    }

    /**
     * Verifica que actualizar parcialmente una deuda inexistente retorna código 404.
     */
    @Test
    void actualizarParcial_debeRetornar404_cuandoNoExiste() throws Exception {
        Long id = 999L;
        Map<String, Object> campos = Map.of("monto", 50000.0);
        when(deudaSocioService.actualizarParcial(eq(id), anyMap())).thenReturn(null);

        mockMvc.perform(patch("/api/v1/deudas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Deuda no encontrada"));

        verify(deudaSocioService).actualizarParcial(eq(id), anyMap());
    }
}
