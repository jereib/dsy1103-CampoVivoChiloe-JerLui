package com.example.ms.insumos.service;

import com.example.ms.insumos.dto.InsumoRequestDTO;
import com.example.ms.insumos.model.Insumo;
import com.example.ms.insumos.repository.InsumoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
 * Pruebas unitarias para el servicio InsumoService.
 * Verifica la lógica de negocio de creación, consulta, actualización y eliminación de insumos.
 */
@ExtendWith(MockitoExtension.class)
class InsumoServiceTest {

    @Mock
    private InsumoRepository repository;

    @InjectMocks
    private InsumoService insumoService;

    /**
     * Verifica que crear un insumo mapea correctamente los campos del DTO y lo guarda.
     */
    @Test
    void crearInsumo_mapeaCamposDTOYGuarda() {
        // given
        InsumoRequestDTO dto = new InsumoRequestDTO();
        dto.setNombre("Tornillos de acero 1/2");
        dto.setDescripcion("Caja de 100 unidades");
        dto.setStock(50);
        dto.setPrecioUnidad(4000.0);

        Insumo insumoGuardado = new Insumo();
        insumoGuardado.setId(1L);
        insumoGuardado.setNombre("Tornillos de acero 1/2");
        insumoGuardado.setDescripcion("Caja de 100 unidades");
        insumoGuardado.setStock(50);
        insumoGuardado.setPrecioUnidad(4000.0);

        when(repository.save(any(Insumo.class))).thenReturn(insumoGuardado);

        // when
        Insumo resultado = insumoService.crearInsumo(dto);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tornillos de acero 1/2", resultado.getNombre());
        assertEquals("Caja de 100 unidades", resultado.getDescripcion());
        assertEquals(50, resultado.getStock());
        assertEquals(4000.0, resultado.getPrecioUnidad());

        ArgumentCaptor<Insumo> captor = ArgumentCaptor.forClass(Insumo.class);
        verify(repository).save(captor.capture());
        Insumo insumoCapturado = captor.getValue();
        assertEquals("Tornillos de acero 1/2", insumoCapturado.getNombre());
        assertEquals("Caja de 100 unidades", insumoCapturado.getDescripcion());
        assertEquals(50, insumoCapturado.getStock());
        assertEquals(4000.0, insumoCapturado.getPrecioUnidad());
    }

    /**
     * Verifica que obtenerPorId retorna el insumo cuando existe.
     */
    @Test
    void obtenerPorId_retornaInsumoCuandoExiste() {
        // given
        Long id = 1L;
        Insumo insumo = new Insumo();
        insumo.setId(id);
        insumo.setNombre("Tornillos de acero 1/2");
        when(repository.findById(id)).thenReturn(Optional.of(insumo));

        // when
        Insumo resultado = insumoService.obtenerPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Tornillos de acero 1/2", resultado.getNombre());
        verify(repository).findById(id);
    }

    /**
     * Verifica que obtenerPorId lanza una excepción cuando el insumo no existe.
     */
    @Test
    void obtenerPorId_lanzaExcepcionCuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> insumoService.obtenerPorId(id));
        assertTrue(exception.getMessage().contains("Insumo no encontrado"));
        verify(repository).findById(id);
    }

    /**
     * Verifica que listarTodos retorna todos los insumos registrados.
     */
    @Test
    void listarTodos_retornaTodos() {
        // given
        List<Insumo> insumos = List.of(new Insumo(), new Insumo());
        when(repository.findAll()).thenReturn(insumos);

        // when
        List<Insumo> resultado = insumoService.listarTodos();

        // then
        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    /**
     * Verifica que actualizar un insumo modifica sus datos cuando existe.
     */
    @Test
    void actualizarInsumo_actualizaCuandoExiste() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        InsumoRequestDTO dto = new InsumoRequestDTO();
        dto.setNombre("Actualizado");
        dto.setDescripcion("Desc actualizada");
        dto.setStock(20);
        dto.setPrecioUnidad(2000.0);

        Insumo insumoActualizado = new Insumo();
        insumoActualizado.setId(id);
        insumoActualizado.setNombre("Actualizado");
        insumoActualizado.setDescripcion("Desc actualizada");
        insumoActualizado.setStock(20);
        insumoActualizado.setPrecioUnidad(2000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenReturn(insumoActualizado);

        // when
        Insumo resultado = insumoService.actualizarInsumo(id, dto);

        // then
        assertNotNull(resultado);
        assertEquals("Actualizado", resultado.getNombre());
        assertEquals("Desc actualizada", resultado.getDescripcion());
        assertEquals(20, resultado.getStock());
        assertEquals(2000.0, resultado.getPrecioUnidad());
        verify(repository).findById(id);
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizar un insumo que no existe lanza una excepción.
     */
    @Test
    void actualizarInsumo_lanzaExcepcionCuandoNoExiste() {
        // given
        Long id = 999L;
        InsumoRequestDTO dto = new InsumoRequestDTO();
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> insumoService.actualizarInsumo(id, dto));
        verify(repository).findById(id);
        verify(repository, never()).save(any());
    }

    /**
     * Verifica que eliminar un insumo lo remueve cuando existe.
     */
    @Test
    void eliminarInsumo_eliminaCuandoExiste() {
        // given
        Long id = 1L;
        Insumo insumo = new Insumo();
        insumo.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(insumo));
        doNothing().when(repository).delete(insumo);

        // when
        insumoService.eliminarInsumo(id);

        // then
        verify(repository).findById(id);
        verify(repository).delete(insumo);
    }

    /**
     * Verifica que eliminar un insumo que no existe lanza una excepción.
     */
    @Test
    void eliminarInsumo_lanzaExcepcionCuandoNoExiste() {
        // given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> insumoService.eliminarInsumo(id));
        verify(repository).findById(id);
        verify(repository, never()).delete(any());
    }

    /**
     * Verifica que actualizarParcial modifica solo el campo nombre.
     */
    @Test
    void actualizarParcial_actualizaCampoNombre() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", "Tornillos galvanizados");

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Tornillos galvanizados", resultado.getNombre());
        assertEquals("Desc original", resultado.getDescripcion());
        assertEquals(10, resultado.getStock());
        assertEquals(1000.0, resultado.getPrecioUnidad());
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizarParcial modifica solo el campo descripcion.
     */
    @Test
    void actualizarParcial_actualizaCampoDescripcion() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("descripcion", "Nueva descripción detallada");

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Nueva descripción detallada", resultado.getDescripcion());
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizarParcial modifica solo el campo stock.
     */
    @Test
    void actualizarParcial_actualizaCampoStock() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("stock", 100);

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(100, resultado.getStock());
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizarParcial modifica solo el campo precioUnidad.
     */
    @Test
    void actualizarParcial_actualizaCampoPrecioUnidad() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("precioUnidad", 5000.0);

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(5000.0, resultado.getPrecioUnidad());
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizarParcial ignora campos que no pertenecen al insumo.
     */
    @Test
    void actualizarParcial_ignoraCampoDesconocido() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("campoInexistente", "valor");

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Original", resultado.getNombre());
        assertEquals("Desc original", resultado.getDescripcion());
        assertEquals(10, resultado.getStock());
        assertEquals(1000.0, resultado.getPrecioUnidad());
        verify(repository).save(insumoExistente);
    }

    /**
     * Verifica que actualizarParcial ignora campos cuyo valor es null.
     */
    @Test
    void actualizarParcial_ignoraCamposConValorNull() {
        // given
        Long id = 1L;
        Insumo insumoExistente = new Insumo();
        insumoExistente.setId(id);
        insumoExistente.setNombre("Original");
        insumoExistente.setDescripcion("Desc original");
        insumoExistente.setStock(10);
        insumoExistente.setPrecioUnidad(1000.0);

        when(repository.findById(id)).thenReturn(Optional.of(insumoExistente));
        when(repository.save(any(Insumo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombre", null);
        campos.put("stock", null);

        // when
        Insumo resultado = insumoService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Original", resultado.getNombre());
        assertEquals(10, resultado.getStock());
        verify(repository).save(insumoExistente);
    }
}
