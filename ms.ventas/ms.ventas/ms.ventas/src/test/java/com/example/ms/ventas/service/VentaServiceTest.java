package com.example.ms.ventas.service;

import com.example.ms.ventas.client.ProductoClient;
import com.example.ms.ventas.dto.ProductoDTO;
import com.example.ms.ventas.dto.VentaRequestDTO;
import com.example.ms.ventas.model.Venta;
import com.example.ms.ventas.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository repository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private VentaService ventaService;

    @Captor
    private ArgumentCaptor<Venta> ventaCaptor;

    @Test
    void registrarVenta_guardaVenta_cuandoProductoExisteYStockSuficiente() {
        // given
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setProductoId(1L);
        dto.setCantidad(5);
        dto.setCanal("WEB");

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setNombre("Pan");
        productoDTO.setPrecio(1200.0);
        productoDTO.setStock(10.0);

        Venta ventaGuardada = new Venta();
        ventaGuardada.setId(1L);
        ventaGuardada.setProductoId(1L);
        ventaGuardada.setCantidad(5);
        ventaGuardada.setTotal(6000.0);
        ventaGuardada.setCanal("WEB");

        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoDTO);
        when(repository.save(any(Venta.class))).thenReturn(ventaGuardada);

        // when
        Venta resultado = ventaService.registrarVenta(dto);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(5, resultado.getCantidad());
        assertEquals("WEB", resultado.getCanal());
        verify(productoClient).obtenerProductoPorId(1L);
        verify(repository).save(any(Venta.class));
    }

    @Test
    void registrarVenta_lanzaExcepcion_cuandoProductoNoExiste() {
        // given
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setProductoId(999L);
        dto.setCantidad(5);
        dto.setCanal("WEB");

        when(productoClient.obtenerProductoPorId(999L)).thenThrow(new RuntimeException("Producto no encontrado"));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.registrarVenta(dto));
        verify(productoClient).obtenerProductoPorId(999L);
        verify(repository, never()).save(any());
    }

    @Test
    void registrarVenta_lanzaExcepcion_cuandoStockInsuficiente() {
        // given
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setProductoId(1L);
        dto.setCantidad(20);
        dto.setCanal("WEB");

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setNombre("Pan");
        productoDTO.setPrecio(1200.0);
        productoDTO.setStock(5.0);

        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoDTO);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.registrarVenta(dto));
        verify(productoClient).obtenerProductoPorId(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void listarTodas_retornaTodasLasVentas() {
        // given
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(2L);
        when(repository.findAll()).thenReturn(List.of(venta1, venta2));

        // when
        List<Venta> resultado = ventaService.listarTodas();

        // then
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void obtenerPorId_retornaVenta_cuandoExiste() {
        // given
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(venta));

        // when
        Venta resultado = ventaService.obtenerPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(repository).findById(id);
    }

    @Test
    void obtenerPorId_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.obtenerPorId(id));
        verify(repository).findById(id);
    }

    @Test
    void actualizarVenta_actualizaVenta_cuandoExiste() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setCantidad(5);
        ventaExistente.setCanal("WEB");

        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setCantidad(10);
        dto.setCanal("FISICO");

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(repository.save(any(Venta.class))).thenReturn(ventaExistente);

        // when
        Venta resultado = ventaService.actualizarVenta(id, dto);

        // then
        assertNotNull(resultado);
        assertEquals(10, resultado.getCantidad());
        assertEquals("FISICO", resultado.getCanal());
        verify(repository).findById(id);
        verify(repository).save(ventaExistente);
    }

    @Test
    void eliminarVenta_eliminaVenta_cuandoExiste() {
        // given
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(venta));

        // when
        ventaService.eliminarVenta(id);

        // then
        verify(repository).findById(id);
        verify(repository).delete(venta);
    }

    @Test
    void eliminarVenta_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.eliminarVenta(id));
        verify(repository).findById(id);
        verify(repository, never()).delete(any());
    }

    @Test
    void actualizarParcial_actualizaCantidadYRecalculaTotal_cuandoCambiaCantidad() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setPrecio(1200.0);
        productoDTO.setStock(20.0);

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoDTO);
        when(repository.save(any(Venta.class))).thenReturn(ventaExistente);

        Map<String, Object> campos = new HashMap<>();
        campos.put("cantidad", 10);

        // when
        Venta resultado = ventaService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(10, resultado.getCantidad());
        assertEquals(12000.0, resultado.getTotal());
        verify(repository).findById(id);
        verify(productoClient).obtenerProductoPorId(1L);
        verify(repository).save(ventaExistente);
    }

    @Test
    void actualizarParcial_lanzaExcepcion_cuandoStockInsuficiente() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setPrecio(1200.0);
        productoDTO.setStock(3.0);

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoDTO);

        Map<String, Object> campos = new HashMap<>();
        campos.put("cantidad", 10);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.actualizarParcial(id, campos));
        verify(repository).findById(id);
        verify(productoClient).obtenerProductoPorId(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void actualizarParcial_lanzaExcepcion_cuandoFeignFalla() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(productoClient.obtenerProductoPorId(1L)).thenThrow(new RuntimeException("Timeout"));

        Map<String, Object> campos = new HashMap<>();
        campos.put("cantidad", 10);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.actualizarParcial(id, campos));
        verify(repository).findById(id);
        verify(productoClient).obtenerProductoPorId(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void actualizarParcial_actualizaCanal_sinRecalcularTotal() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(repository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("canal", "FISICO");

        // when
        Venta resultado = ventaService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("FISICO", resultado.getCanal());
        assertEquals(5, resultado.getCantidad());
        assertEquals(6000.0, resultado.getTotal());
        verify(repository).findById(id);
        verify(repository).save(ventaExistente);
        verify(productoClient, never()).obtenerProductoPorId(anyLong());
    }

    @Test
    void actualizarParcial_ignoraProductoId() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(repository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("productoId", 999L);

        // when
        Venta resultado = ventaService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getProductoId());
        verify(repository).findById(id);
        verify(repository).save(ventaExistente);
        verify(productoClient, never()).obtenerProductoPorId(anyLong());
    }

    @Test
    void actualizarParcial_ignoraCampoDesconocido() {
        // given
        Long id = 1L;
        Venta ventaExistente = new Venta();
        ventaExistente.setId(id);
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(5);
        ventaExistente.setTotal(6000.0);
        ventaExistente.setCanal("WEB");

        when(repository.findById(id)).thenReturn(Optional.of(ventaExistente));
        when(repository.save(any(Venta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("campoInexistente", "valor");

        // when
        Venta resultado = ventaService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidad());
        assertEquals("WEB", resultado.getCanal());
        verify(repository).findById(id);
        verify(repository).save(ventaExistente);
        verify(productoClient, never()).obtenerProductoPorId(anyLong());
    }

    @Test
    void actualizarVenta_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        VentaRequestDTO dto = new VentaRequestDTO();
        dto.setCantidad(10);
        dto.setCanal("FISICO");

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> ventaService.actualizarVenta(id, dto));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }
}
