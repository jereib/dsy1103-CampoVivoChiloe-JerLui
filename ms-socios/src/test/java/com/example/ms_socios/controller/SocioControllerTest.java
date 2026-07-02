package com.example.ms_socios.controller;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.service.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SocioController.class)
class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SocioService socioService;

    @Test
    void listarSocios_debeRetornarLista() throws Exception {
        List<Socio> socios = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE)
        );
        when(socioService.listarSocios()).thenReturn(socios);

        mockMvc.perform(get("/api/v1/socios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].socio").value("Los Krausse"));
    }

    @Test
    void buscarPorId_debeRetornarSocio() throws Exception {
        Socio socio = new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioService.buscarPorId(1L)).thenReturn(socio);

        mockMvc.perform(get("/api/v1/socios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socio").value("Los Krausse"));
    }

    @Test
    void buscarPorId_debeRetornar400_cuandoNoExiste() throws Exception {
        when(socioService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/socios/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El id ingresado no existe"));
    }

    @Test
    void obtenerSocioPorEstado_debeRetornarListaFiltrada() throws Exception {
        List<Socio> socios = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE)
        );
        when(socioService.buscarPorEstado(Estado.DISPONIBLE)).thenReturn(socios);

        mockMvc.perform(get("/api/v1/socios/estado/DISPONIBLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void obtenerSocioPorEstado_debeRetornar400_cuandoEstadoInvalido() throws Exception {
        mockMvc.perform(get("/api/v1/socios/estado/INVALIDO"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El estado ingresado no existe"));
    }

    @Test
    void guardarSocio_debeRetornar200() throws Exception {
        Socio guardado = new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioService.guardarSocio(any(Socio.class))).thenReturn(guardado);

        String json = """
                {
                    "socio": "Los Krausse",
                    "predio": "Los abetos",
                    "capacidad": 15,
                    "estado": "DISPONIBLE"
                }
                """;

        mockMvc.perform(post("/api/v1/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socio").value("Los Krausse"));
    }

    @Test
    void guardarSocio_debeRetornar400_cuandoDuplicado() throws Exception {
        when(socioService.guardarSocio(any(Socio.class))).thenReturn(null);

        String json = """
                {
                    "socio": "Los Krausse",
                    "predio": "Los abetos",
                    "capacidad": 15,
                    "estado": "DISPONIBLE"
                }
                """;

        mockMvc.perform(post("/api/v1/socios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ya existe un socio con ese nombre"));
    }

    @Test
    void actualizarSocio_debeRetornar200() throws Exception {
        Socio actualizado = new Socio(1L, "Los Krausse Updated", "El mirador", 20, Estado.SUSPENDIDO);
        when(socioService.actualizarPorId(anyLong(), any(Socio.class))).thenReturn(actualizado);

        String json = """
                {
                    "socio": "Los Krausse Updated",
                    "predio": "El mirador",
                    "capacidad": 20,
                    "estado": "SUSPENDIDO"
                }
                """;

        mockMvc.perform(put("/api/v1/socios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socio").value("Los Krausse Updated"));
    }

    @Test
    void actualizarSocio_debeRetornar400_cuandoNoExiste() throws Exception {
        when(socioService.actualizarPorId(anyLong(), any(Socio.class))).thenReturn(null);

        String json = """
                {
                    "socio": "Los Krausse Updated",
                    "predio": "El mirador",
                    "capacidad": 20,
                    "estado": "SUSPENDIDO"
                }
                """;

        mockMvc.perform(put("/api/v1/socios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El socio no existe"));
    }

    @Test
    void eliminarSocio_debeRetornar200() throws Exception {
        when(socioService.eliminarSocio(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/socios/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Socio eliminado correctamente"));
    }

    @Test
    void eliminarSocio_debeRetornar400_cuandoNoExiste() throws Exception {
        when(socioService.eliminarSocio(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/socios/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El socio no existe"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void actualizarParcialSocio_debeRetornar200() throws Exception {
        Socio actualizado = new Socio(1L, "Los Krausse Modificado", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioService.actualizarParcial(anyLong(), any(Map.class))).thenReturn(actualizado);

        String json = """
                {
                    "socio": "Los Krausse Modificado"
                }
                """;

        mockMvc.perform(patch("/api/v1/socios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socio").value("Los Krausse Modificado"));
    }

    @SuppressWarnings("unchecked")
    @Test
    void actualizarParcialSocio_debeRetornar404_cuandoNoExiste() throws Exception {
        when(socioService.actualizarParcial(anyLong(), any(Map.class))).thenReturn(null);

        String json = """
                {
                    "socio": "Los Krausse Modificado"
                }
                """;

        mockMvc.perform(patch("/api/v1/socios/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("El socio con el ID 999 no existe"));
    }
}
