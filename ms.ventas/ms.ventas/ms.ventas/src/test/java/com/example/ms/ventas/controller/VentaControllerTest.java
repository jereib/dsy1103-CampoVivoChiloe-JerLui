package com.example.ms.ventas.controller;

import com.example.ms.ventas.dto.VentaRequestDTO;
import com.example.ms.ventas.model.Venta;
import com.example.ms.ventas.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests del controlador de ventas.
 * Verifica que los endpoints REST respondan correctamente
 * y que el servicio sea invocado con los parámetros adecuados.
 */
@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VentaService service;

    /**
     * Al listar todas las ventas, el endpoint retorna 200 con la lista.
     */
    @Test
    void listarTodas_retorna200YLista() throws Exception {
        when(service.listarTodas()).thenReturn(List.of(new Venta(), new Venta()));

        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodas();
    }

    /**
     * Al buscar una venta por id existente, retorna 200 con los datos.
     */
    @Test
    void obtenerPorId_retorna200YVenta() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setCantidad(5);
        venta.setCanal("WEB");
        when(service.obtenerPorId(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(5));

        verify(service).obtenerPorId(1L);
    }

    /**
     * Al crear una venta válida, retorna 201 con los datos guardados.
     */
    @Test
    void crearVenta_retorna201YVenta() throws Exception {
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setProductoId(1L);
        dto.setCantidad(5);
        dto.setCanal("WEB");

        Venta venta = new Venta();
        venta.setId(1L);
        venta.setCantidad(5);
        venta.setTotal(6000.0);
        when(service.registrarVenta(any())).thenReturn(venta);

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).registrarVenta(any());
    }

    /**
     * Al actualizar una venta existente, retorna 200 con los nuevos datos.
     */
    @Test
    void actualizar_retorna200YVenta() throws Exception {
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setProductoId(1L);
        dto.setCantidad(10);
        dto.setCanal("FISICO");

        Venta venta = new Venta();
        venta.setId(1L);
        venta.setCantidad(10);
        venta.setCanal("FISICO");
        when(service.actualizarVenta(eq(1L), any())).thenReturn(venta);

        mockMvc.perform(put("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(10));

        verify(service).actualizarVenta(eq(1L), any());
    }

    /**
     * Al eliminar una venta existente, retorna 204 sin contenido.
     */
    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminarVenta(1L);

        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminarVenta(1L);
    }

    /**
     * Al actualizar parcialmente una venta, retorna 200 con los cambios.
     */
    @Test
    void actualizarParcial_retorna200() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setCantidad(10);
        venta.setTotal(12000.0);
        when(service.actualizarParcial(eq(1L), any())).thenReturn(venta);

        Map<String, Object> campos = Map.of("cantidad", 10);

        mockMvc.perform(patch("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(10));

        verify(service).actualizarParcial(eq(1L), any());
    }

    /**
     * Al actualizar parcialmente con datos inválidos, retorna 400.
     */
    @Test
    void actualizarParcial_retorna400_cuandoIllegalArgument() throws Exception {
        when(service.actualizarParcial(eq(1L), any())).thenThrow(new IllegalArgumentException("Stock insuficiente"));

        Map<String, Object> campos = Map.of("cantidad", 999);

        mockMvc.perform(patch("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isBadRequest());

        verify(service).actualizarParcial(eq(1L), any());
    }
}
