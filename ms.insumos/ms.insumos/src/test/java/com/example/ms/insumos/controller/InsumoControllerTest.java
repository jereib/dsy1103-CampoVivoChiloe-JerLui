package com.example.ms.insumos.controller;

import com.example.ms.insumos.dto.InsumoRequestDTO;
import com.example.ms.insumos.model.Insumo;
import com.example.ms.insumos.service.InsumoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class InsumoControllerTest {

    MockMvc mockMvc;

    @Mock
    InsumoService service;

    @InjectMocks
    InsumoController controller;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void listarTodos_retorna200YLista() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(new Insumo(), new Insumo()));

        mockMvc.perform(get("/api/v1/insumos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    @Test
    void obtenerPorId_retorna200YInsumo() throws Exception {
        Insumo insumo = new Insumo();
        insumo.setId(1L);
        insumo.setNombre("Test");
        when(service.obtenerPorId(1L)).thenReturn(insumo);

        mockMvc.perform(get("/api/v1/insumos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Test"));

        verify(service).obtenerPorId(1L);
    }

    @Test
    void crear_retorna201YInsumo() throws Exception {
        InsumoRequestDTO dto = new InsumoRequestDTO();
        dto.setNombre("Nuevo");
        dto.setDescripcion("Desc");
        dto.setStock(10);
        dto.setPrecioUnidad(500.0);

        Insumo insumo = new Insumo();
        insumo.setId(1L);
        insumo.setNombre("Nuevo");
        when(service.crearInsumo(any())).thenReturn(insumo);

        mockMvc.perform(post("/api/v1/insumos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).crearInsumo(any());
    }

    @Test
    void actualizar_retorna200YInsumo() throws Exception {
        InsumoRequestDTO dto = new InsumoRequestDTO();
        dto.setNombre("Actualizado");
        dto.setDescripcion("Desc");
        dto.setStock(20);
        dto.setPrecioUnidad(600.0);

        Insumo insumo = new Insumo();
        insumo.setId(1L);
        insumo.setNombre("Actualizado");
        when(service.actualizarInsumo(eq(1L), any())).thenReturn(insumo);

        mockMvc.perform(put("/api/v1/insumos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizado"));

        verify(service).actualizarInsumo(eq(1L), any());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminarInsumo(1L);

        mockMvc.perform(delete("/api/v1/insumos/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminarInsumo(1L);
    }

    @Test
    void actualizarParcial_retorna200() throws Exception {
        Insumo insumo = new Insumo();
        insumo.setId(1L);
        insumo.setNombre("Parcial");
        when(service.actualizarParcial(eq(1L), any())).thenReturn(insumo);

        Map<String, Object> campos = Map.of("nombre", "Parcial");

        mockMvc.perform(patch("/api/v1/insumos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Parcial"));

        verify(service).actualizarParcial(eq(1L), any());
    }

    @Test
    void actualizarParcial_retorna404_cuandoNotFound() throws Exception {
        when(service.actualizarParcial(eq(999L), any())).thenThrow(new RuntimeException("Insumo no encontrado"));

        Map<String, Object> campos = Map.of("nombre", "X");

        mockMvc.perform(patch("/api/v1/insumos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isNotFound());

        verify(service).actualizarParcial(eq(999L), any());
    }

    @Test
    void actualizarParcial_retorna400_cuandoError() throws Exception {
        doAnswer(invocation -> {
            throw new Exception("Error checked");
        }).when(service).actualizarParcial(eq(1L), any());

        Map<String, Object> campos = Map.of("stock", "no-valido");

        mockMvc.perform(patch("/api/v1/insumos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isBadRequest());

        verify(service).actualizarParcial(eq(1L), any());
    }
}
