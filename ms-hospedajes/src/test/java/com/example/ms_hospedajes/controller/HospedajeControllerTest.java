package com.example.ms_hospedajes.controller;

import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.service.HospedajeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias del controlador HospedajeController.
 * Verifica que los endpoints respondan correctamente usando MockMvc.
 */
@WebMvcTest(HospedajeController.class)
class HospedajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HospedajeService hospedajeService;

    /**
     * Debe retornar una lista de hospedajes con código 200.
     */
    @Test
    void listar_debeRetornarListaDeHospedajes() throws Exception {
        HospedajeModel h1 = new HospedajeModel();
        h1.setId(1L);
        h1.setSocioId(1L);
        h1.setHuespedId(1L);
        HospedajeModel h2 = new HospedajeModel();
        h2.setId(2L);
        h2.setSocioId(2L);
        h2.setHuespedId(2L);

        when(hospedajeService.listar()).thenReturn(List.of(h1, h2));

        mockMvc.perform(get("/api/v1/hospedajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    /**
     * Debe retornar un DTO de huésped al consultar por ID.
     */
    @Test
    void obtenerHuesped_debeRetornarHuespedDTO() throws Exception {
        HuespedDTO dto = new HuespedDTO();
        dto.setId(1L);
        dto.setNombreCompleto("Benjamin agüero");
        dto.setRut("21659428-2");
        dto.setCorreo("benja123@gmail.com");

        when(hospedajeService.obtenerHuesped(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/hospedajes/huespedes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreCompleto").value("Benjamin agüero"));
    }

    /**
     * Debe retornar un DTO de socio al consultar por ID.
     */
    @Test
    void obtenerSocio_debeRetornarSocioDTO() throws Exception {
        SocioDTO dto = new SocioDTO();
        dto.setId(1L);
        dto.setSocio("Los Jackson");
        dto.setPredio("Los avellanos");
        dto.setCapacidad(10);

        when(hospedajeService.obtenerSocio(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/hospedajes/socios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.socio").value("Los Jackson"));
    }

    /**
     * Debe retornar 201 al crear un hospedaje vía GET.
     */
    @Test
    void crearHospedajeGet_debeRetornar201() throws Exception {
        when(hospedajeService.crearHospedaje(1L, 2L))
                .thenReturn("Hospedaje creado para el huésped: Benjamín | Familia socia: Los Jackson");

        mockMvc.perform(get("/api/v1/hospedajes/crear/1/2"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Hospedaje creado para el huésped: Benjamín | Familia socia: Los Jackson"));
    }

    /**
     * Debe retornar 404 si hay error al crear un hospedaje vía GET.
     */
    @Test
    void crearHospedajeGet_debeRetornar404_cuandoError() throws Exception {
        when(hospedajeService.crearHospedaje(1L, 2L))
                .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/v1/hospedajes/crear/1/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: huesped o socio no encontrado"));
    }

    /**
     * Debe retornar 201 al crear un hospedaje vía POST.
     */
    @Test
    void crearHospedajePost_debeRetornar201() throws Exception {
        when(hospedajeService.crearHospedaje(1L, 2L))
                .thenReturn("Hospedaje creado para el huésped: Benjamín | Familia socia: Los Jackson");

        String json = """
                {
                    "socioId": 1,
                    "huespedId": 2
                }
                """;

        mockMvc.perform(post("/api/v1/hospedajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Hospedaje creado para el huésped: Benjamín | Familia socia: Los Jackson"));
    }

    /**
     * Debe retornar 404 si hay error al crear un hospedaje vía POST.
     */
    @Test
    void crearHospedajePost_debeRetornar404_cuandoError() throws Exception {
        when(hospedajeService.crearHospedaje(anyLong(), anyLong()))
                .thenThrow(new RuntimeException("Error"));

        String json = """
                {
                    "socioId": 1,
                    "huespedId": 2
                }
                """;

        mockMvc.perform(post("/api/v1/hospedajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Error: huesped o socio no encontrado"));
    }

    /**
     * Debe retornar el hospedaje actualizado al modificar uno existente.
     */
    @Test
    void actualizar_debeRetornarHospedajeActualizado() throws Exception {
        HospedajeModel actualizado = new HospedajeModel();
        actualizado.setId(1L);
        actualizado.setSocioId(2L);
        actualizado.setHuespedId(2L);

        when(hospedajeService.actualizar(anyLong(), any(HospedajeModel.class))).thenReturn(actualizado);

        String json = """
                {
                    "socioId": 2,
                    "huespedId": 2
                }
                """;

        mockMvc.perform(put("/api/v1/hospedajes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.socioId").value(2));
    }

    /**
     * Debe retornar 404 al intentar actualizar un hospedaje que no existe.
     */
    @Test
    void actualizar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(hospedajeService.actualizar(anyLong(), any(HospedajeModel.class))).thenReturn(null);

        String json = """
                {
                    "socioId": 2,
                    "huespedId": 2
                }
                """;

        mockMvc.perform(put("/api/v1/hospedajes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Hospedaje no encontrado"));
    }

    /**
     * Debe retornar 204 al eliminar un hospedaje existente.
     */
    @Test
    void eliminar_debeRetornar204() throws Exception {
        when(hospedajeService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/hospedajes/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * Debe retornar 404 al intentar eliminar un hospedaje que no existe.
     */
    @Test
    void eliminar_debeRetornar404_cuandoNoExiste() throws Exception {
        when(hospedajeService.eliminar(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/hospedajes/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Hospedaje no encontrado"));
    }
}
