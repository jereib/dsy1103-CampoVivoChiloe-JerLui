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

/**
 * Pruebas unitarias del servicio ProductoService.
 * Verifica la lógica de negocio incluyendo la validación del margen de precio.
 */
@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService productoService;

    /**
     * crearProducto debe guardar el producto cuando el precio supera el margen mínimo del 20%.
     */
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

    /**
     * crearProducto debe lanzar excepción cuando el precio no cumple el margen mínimo del 20%.
     */
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

    /**
     * obtenerProductoPorId debe retornar el producto cuando existe en la base de datos.
     */
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

    /**
     * obtenerProductoPorId debe lanzar excepción cuando el producto no existe.
     */
    @Test
    void obtenerProductoPorId_lanzaExcepcion_cuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class, () -> productoService.obtenerProductoPorId(id));
        verify(repository).findById(id);
    }

    /**
     * listarTodos debe retornar todos los productos registrados.
     */
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

    /**
     * actualizarProducto debe actualizar el producto cuando el margen de precio es válido.
     */
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

    /**
     * actualizarProducto debe lanzar excepción cuando el nuevo precio no cumple el margen.
     */
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

    /**
     * actualizarProducto debe lanzar excepción cuando el producto no existe.
     */
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

    /**
     * eliminarProducto debe eliminar el producto cuando existe.
     */
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

    /**
     * eliminarProducto debe lanzar excepción cuando el producto no existe.
     */
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

    /**
     * actualizarParcial debe actualizar el nombre cuando el margen sigue siendo válido.
     */
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

    /**
     * actualizarParcial debe validar el margen cuando se actualiza el precio.
     */
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

    /**
     * actualizarParcial debe lanzar excepción cuando el margen deja de cumplirse.
     */
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

    /**
     * actualizarParcial debe actualizar el stock correctamente.
     */
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

    /**
     * actualizarParcial debe actualizar el costo de producción correctamente.
     */
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

    /**
     * actualizarParcial debe lanzar excepción cuando el nuevo costo de producción hace fallar el margen.
     */
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

    /**
     * actualizarParcial debe ignorar campos no reconocidos sin lanzar error.
     */
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
