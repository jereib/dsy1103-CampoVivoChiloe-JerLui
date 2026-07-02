package com.example.productos.service;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void crearProducto_guardaProducto_cuandoPrecioSuperaMargenMinimo() {
        // given
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan");
        dto.setPrecio(1200.0);
        dto.setStock(15.0);
        dto.setCostoProduccion(1000.0);

        Producto productoGuardado = new Producto(1L, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.save(any(Producto.class))).thenReturn(productoGuardado);

        // when
        Producto resultado = productoService.crearProducto(dto);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Pan", resultado.getNombre());
        assertEquals(1200.0, resultado.getPrecio());
        verify(repository).save(any(Producto.class));
    }

    @Test
    void crearProducto_lanzaExcepcion_cuandoPrecioNoCumpleMargenMinimo() {
        // given
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan");
        dto.setPrecio(1000.0);
        dto.setStock(15.0);
        dto.setCostoProduccion(1000.0);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.crearProducto(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void obtenerProductoPorId_retornaProducto_cuandoExiste() {
        // given
        Long id = 1L;
        Producto producto = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(producto));

        // when
        Producto resultado = productoService.obtenerProductoPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Pan", resultado.getNombre());
        verify(repository).findById(id);
    }

    @Test
    void obtenerProductoPorId_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.obtenerProductoPorId(id));
        verify(repository).findById(id);
    }

    @Test
    void listarTodos_retornaTodosLosProductos() {
        // given
        List<Producto> productos = List.of(
                new Producto(1L, "Pan", 1200.0, 15.0, 1000.0),
                new Producto(2L, "Leche", 800.0, 20.0, 600.0)
        );
        when(repository.findAll()).thenReturn(productos);

        // when
        List<Producto> resultado = productoService.listarTodos();

        // then
        assertEquals(2, resultado.size());
        assertEquals(productos, resultado);
        verify(repository).findAll();
    }

    @Test
    void actualizarProducto_actualizaProducto_cuandoMargenEsValido() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan Artesanal");
        dto.setPrecio(1500.0);
        dto.setStock(10.0);
        dto.setCostoProduccion(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenReturn(productoExistente);

        // when
        Producto resultado = productoService.actualizarProducto(id, dto);

        // then
        assertNotNull(resultado);
        assertEquals("Pan Artesanal", resultado.getNombre());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }

    @Test
    void actualizarProducto_lanzaExcepcion_cuandoMargenNoCumple() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan Barato");
        dto.setPrecio(1000.0);
        dto.setStock(15.0);
        dto.setCostoProduccion(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.actualizarProducto(id, dto));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void actualizarProducto_lanzaExcepcion_cuandoProductoNoEncontrado() {
        // given
        Long id = 999L;
        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Pan");
        dto.setPrecio(1200.0);
        dto.setStock(15.0);
        dto.setCostoProduccion(1000.0);

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.actualizarProducto(id, dto));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void eliminarProducto_eliminaProducto_cuandoExiste() {
        // given
        Long id = 1L;
        Producto producto = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(producto));

        // when
        productoService.eliminarProducto(id);

        // then
        verify(repository).findById(id);
        verify(repository).delete(producto);
    }

    @Test
    void eliminarProducto_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.eliminarProducto(id));
        verify(repository).findById(id);
        verify(repository, never()).delete(any());
    }

    @Test
    void actualizarParcial_actualizaNombre_cuandoMargenSigueSiendoValido() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenReturn(productoExistente);

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Pan Integral");

        // when
        Producto resultado = productoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Pan Integral", resultado.getNombre());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }

    @Test
    void actualizarParcial_validaMargen_cuandoSeActualizaPrecio() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenReturn(productoExistente);

        Map<String, Object> campos = new HashMap<>();
        campos.put("precio", "1400.0");

        // when
        Producto resultado = productoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(1400.0, resultado.getPrecio());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }

    @Test
    void actualizarParcial_lanzaExcepcion_cuandoMargenFalla() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));

        Map<String, Object> campos = new HashMap<>();
        campos.put("precio", "1000.0");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.actualizarParcial(id, campos));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void actualizarParcial_actualizaStock() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("stock", "20.0");

        // when
        Producto resultado = productoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(20.0, resultado.getStock());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }

    @Test
    void actualizarParcial_actualizaCostoProduccion() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("costoProduccion", "500.0");

        // when
        Producto resultado = productoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(500.0, resultado.getCostoProduccion());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }

    @Test
    void actualizarParcial_lanzaExcepcion_cuandoCostoProduccionHaceFallarMargen() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));

        Map<String, Object> campos = new HashMap<>();
        campos.put("costoProduccion", "2000.0");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.actualizarParcial(id, campos));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void actualizarParcial_ignoraCampoDesconocido() {
        // given
        Long id = 1L;
        Producto productoExistente = new Producto(id, "Pan", 1200.0, 15.0, 1000.0);
        when(repository.findById(id)).thenReturn(Optional.of(productoExistente));
        when(repository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("campoInexistente", "valor");

        // when
        Producto resultado = productoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Pan", resultado.getNombre());
        assertEquals(1200.0, resultado.getPrecio());
        assertEquals(15.0, resultado.getStock());
        assertEquals(1000.0, resultado.getCostoProduccion());
        verify(repository).findById(id);
        verify(repository).save(productoExistente);
    }
}
