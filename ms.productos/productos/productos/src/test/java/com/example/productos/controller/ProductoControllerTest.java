package com.example.productos.controller;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
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
 * Pruebas unitarias del controlador ProductoController.
 * Verifica que los endpoints respondan correctamente y deleguen en el servicio.
 */
@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductoService service;

    /**
     * GET /api/v1/productos debe retornar 200 con la lista de productos.
     */
    @Test
    void listarTodos_retorna200YLista() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(new Producto(), new Producto()));

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(service).listarTodos();
    }

    /**
     * GET /api/v1/productos/{id} debe retornar 200 con el producto correspondiente.
     */
    @Test
    void obtenerPorId_retorna200YProducto() throws Exception {
        Producto producto = new Producto(1L, "Pan", 1200.0, 15.0, 1000.0);
        when(service.obtenerProductoPorId(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/v1/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pan"));

        verify(service).obtenerProductoPorId(1L);
    }

    /**
     * POST /api/v1/productos debe retornar 201 con el producto creado.
     */
    @Test
    void crear_retorna201YProducto() throws Exception {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan");
        dto.setPrecio(1200.0);
        dto.setStock(15.0);
        dto.setCostoProduccion(1000.0);

        Producto producto = new Producto(1L, "Pan", 1200.0, 15.0, 1000.0);
        when(service.crearProducto(any())).thenReturn(producto);

        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).crearProducto(any());
    }

    /**
     * PUT /api/v1/productos/{id} debe retornar 200 con el producto actualizado.
     */
    @Test
    void actualizar_retorna200YProducto() throws Exception {
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan Artesanal");
        dto.setPrecio(1500.0);
        dto.setStock(10.0);
        dto.setCostoProduccion(1000.0);

        Producto producto = new Producto(1L, "Pan Artesanal", 1500.0, 10.0, 1000.0);
        when(service.actualizarProducto(eq(1L), any())).thenReturn(producto);

        mockMvc.perform(put("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pan Artesanal"));

        verify(service).actualizarProducto(eq(1L), any());
    }

    /**
     * DELETE /api/v1/productos/{id} debe retornar 204 sin contenido.
     */
    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminarProducto(1L);

        mockMvc.perform(delete("/api/v1/productos/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminarProducto(1L);
    }

    /**
     * PATCH /api/v1/productos/{id} debe retornar 200 con el producto modificado.
     */
    @Test
    void actualizarParcial_retorna200() throws Exception {
        Producto producto = new Producto(1L, "Pan Integral", 1200.0, 15.0, 1000.0);
        when(service.actualizarParcial(eq(1L), any())).thenReturn(producto);

        Map<String, Object> campos = Map.of("nombre", "Pan Integral");

        mockMvc.perform(patch("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pan Integral"));

        verify(service).actualizarParcial(eq(1L), any());
    }

    /**
     * PATCH /api/v1/productos/{id} debe retornar 400 cuando el servicio lanza IllegalArgumentException.
     */
    @Test
    void actualizarParcial_retorna400_cuandoIllegalArgument() throws Exception {
        when(service.actualizarParcial(eq(1L), any())).thenThrow(new IllegalArgumentException("Margen inválido"));

        Map<String, Object> campos = Map.of("precio", 1000.0);

        mockMvc.perform(patch("/api/v1/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campos)))
                .andExpect(status().isBadRequest());

        verify(service).actualizarParcial(eq(1L), any());
    }
}
