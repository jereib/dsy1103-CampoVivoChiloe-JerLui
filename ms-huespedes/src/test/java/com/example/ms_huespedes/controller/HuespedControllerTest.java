package com.example.ms_huespedes.controller;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.service.HuespedService;
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

/**
 * Pruebas unitarias del controlador HuespedController.
 * Simula peticiones HTTP y verifica las respuestas del controlador.
 */
@WebMvcTest(HuespedController.class)
class HuespedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HuespedService huespedService;

    /**
     * Verifica que listar todos los huéspedes retorna una lista con código 200.
     */
    @Test
    void listarHuespedes_debeRetornarLista() throws Exception {
        List<Huesped> huespedes = List.of(
                new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com")
        );
        when(huespedService.listarHuespedes()).thenReturn(huespedes);

        mockMvc.perform(get("/api/v1/huespedes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombreCompleto").value("Benjamin agüero"));
    }

    /**
     * Verifica que buscar un huésped por id retorna el huésped con código 200.
     */
    @Test
    void buscarPorId_debeRetornarHuesped() throws Exception {
        Huesped huesped = new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedService.buscarPorId(1L)).thenReturn(huesped);

        mockMvc.perform(get("/api/v1/huespedes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Benjamin agüero"));
    }

    /**
     * Verifica que buscar un huésped inexistente retorna código 400.
     */
    @Test
    void buscarPorId_debeRetornar400_cuandoNoExiste() throws Exception {
        when(huespedService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/api/v1/huespedes/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El id ingresado no existe"));
    }

    /**
     * Verifica que guardar un huésped retorna código 200.
     */
    @Test
    void guardarHuesped_debeRetornar200() throws Exception {
        Huesped guardado = new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedService.guardarHuesped(any(Huesped.class))).thenReturn(guardado);

        String json = """
                {
                    "nombreCompleto": "Benjamin agüero",
                    "rut": "21659428-2",
                    "edad": 21,
                    "perfil": "chileno",
                    "historial": "historial",
                    "correo": "benja123@gmail.com"
                }
                """;

        mockMvc.perform(post("/api/v1/huespedes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Benjamin agüero"));
    }

    /**
     * Verifica que guardar un huésped duplicado retorna código 400.
     */
    @Test
    void guardarHuesped_debeRetornar400_cuandoDuplicado() throws Exception {
        when(huespedService.guardarHuesped(any(Huesped.class))).thenReturn(null);

        String json = """
                {
                    "nombreCompleto": "Benjamin agüero",
                    "rut": "21659428-2",
                    "edad": 21,
                    "perfil": "chileno",
                    "historial": "historial",
                    "correo": "benja123@gmail.com"
                }
                """;

        mockMvc.perform(post("/api/v1/huespedes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ya existe un huesped con ese nombre"));
    }

    /**
     * Verifica que actualizar un huésped existente retorna código 200.
     */
    @Test
    void actualizarHuesped_debeRetornar200() throws Exception {
        Huesped actualizado = new Huesped(1L, "Benjamin agüero update", "21659428-2", 22, "turista", "nuevo historial", "nuevo@gmail.com");
        when(huespedService.actualizarPorId(anyLong(), any(Huesped.class))).thenReturn(actualizado);

        String json = """
                {
                    "nombreCompleto": "Benjamin agüero update",
                    "rut": "21659428-2",
                    "edad": 22,
                    "perfil": "turista",
                    "historial": "nuevo historial",
                    "correo": "nuevo@gmail.com"
                }
                """;

        mockMvc.perform(put("/api/v1/huespedes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Benjamin agüero update"));
    }

    /**
     * Verifica que actualizar un huésped inexistente retorna código 400.
     */
    @Test
    void actualizarHuesped_debeRetornar400_cuandoNoExiste() throws Exception {
        when(huespedService.actualizarPorId(anyLong(), any(Huesped.class))).thenReturn(null);

        String json = """
                {
                    "nombreCompleto": "Benjamin agüero update",
                    "rut": "21659428-2",
                    "edad": 22,
                    "perfil": "turista",
                    "historial": "nuevo historial",
                    "correo": "nuevo@gmail.com"
                }
                """;

        mockMvc.perform(put("/api/v1/huespedes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El huesped no existe"));
    }

    /**
     * Verifica que eliminar un huésped existente retorna código 200.
     */
    @Test
    void eliminarHuesped_debeRetornar200() throws Exception {
        when(huespedService.eliminarHuesped(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/huespedes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Huesped eliminado correctamente"));
    }

    /**
     * Verifica que eliminar un huésped inexistente retorna código 400.
     */
    @Test
    void eliminarHuesped_debeRetornar400_cuandoNoExiste() throws Exception {
        when(huespedService.eliminarHuesped(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/huespedes/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El huesped no existe"));
    }

    @SuppressWarnings("unchecked")
    /**
     * Verifica que actualizar parcialmente un huésped retorna código 200.
     */
    @Test
    void actualizarParcialHuesped_debeRetornar200() throws Exception {
        Huesped actualizado = new Huesped(1L, "Benjamin modificado", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedService.actualizarParcial(anyLong(), any(Map.class))).thenReturn(actualizado);

        String json = """
                {
                    "nombreCompleto": "Benjamin modificado"
                }
                """;

        mockMvc.perform(patch("/api/v1/huespedes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCompleto").value("Benjamin modificado"));
    }

    @SuppressWarnings("unchecked")
    /**
     * Verifica que actualizar parcialmente un huésped inexistente retorna código 404.
     */
    @Test
    void actualizarParcialHuesped_debeRetornar404_cuandoNoExiste() throws Exception {
        when(huespedService.actualizarParcial(anyLong(), any(Map.class))).thenReturn(null);

        String json = """
                {
                    "nombreCompleto": "Benjamin modificado"
                }
                """;

        mockMvc.perform(patch("/api/v1/huespedes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("El huésped con el ID 999 no existe"));
    }
}
