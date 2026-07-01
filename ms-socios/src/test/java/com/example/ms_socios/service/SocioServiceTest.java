package com.example.ms_socios.service;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.repository.SocioRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocioServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks
    private SocioService socioService;

    @Test
    void listarSocios_retornaTodosLosSocios() {
        // given
        List<Socio> sociosEsperados = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE),
                new Socio(2L, "Los Jackson", "Los avellanos", 10, Estado.SUSPENDIDO)
        );
        when(socioRepository.findAll()).thenReturn(sociosEsperados);

        // when
        List<Socio> resultado = socioService.listarSocios();

        // then
        assertEquals(2, resultado.size());
        assertEquals(sociosEsperados, resultado);
        verify(socioRepository).findAll();
    }

    @Test
    void buscarPorEstado_retornaSociosFiltradosPorEstado() {
        // given
        Estado estado = Estado.DISPONIBLE;
        List<Socio> sociosFiltrados = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE)
        );
        when(socioRepository.findByEstado(estado)).thenReturn(sociosFiltrados);

        // when
        List<Socio> resultado = socioService.buscarPorEstado(estado);

        // then
        assertEquals(1, resultado.size());
        assertEquals(Estado.DISPONIBLE, resultado.get(0).getEstado());
        verify(socioRepository).findByEstado(estado);
    }

    @Test
    void buscarPorId_retornaSocioCuandoExiste() {
        // given
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));

        // when
        Socio resultado = socioService.buscarPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Los Krausse", resultado.getSocio());
        verify(socioRepository).findById(id);
    }

    @Test
    void buscarPorId_retornaNullCuandoNoExiste() {
        // given
        Long id = 999L;
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Socio resultado = socioService.buscarPorId(id);

        // then
        assertNull(resultado);
        verify(socioRepository).findById(id);
    }

    @Test
    void guardarSocio_guardaYRetornaCuandoNombreEsUnico() {
        // given
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(false);
        when(socioRepository.save(socio)).thenReturn(socioGuardado);

        // when
        Socio resultado = socioService.guardarSocio(socio);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Los Krausse", resultado.getSocio());
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository).save(socio);
    }

    @Test
    void guardarSocio_retornaNullCuandoNombreYaExiste() {
        // given
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(true);

        // when
        Socio resultado = socioService.guardarSocio(socio);

        // then
        assertNull(resultado);
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository, never()).save(any());
    }

    @Test
    void guardarSocio_relanzaExcepcionCuandoRepositorioFalla() {
        // given
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(false);
        when(socioRepository.save(socio)).thenThrow(new RuntimeException("Error de BD"));

        // when & then
        assertThrows(RuntimeException.class, () -> socioService.guardarSocio(socio));
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository).save(socio);
    }

    @Test
    void actualizarPorId_actualizaCuandoSocioExiste() {
        // given
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioActualizado = new Socio(id, "Los Krausse Updated", "El mirador", 20, Estado.SUSPENDIDO);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioActualizado);

        // when
        Socio resultado = socioService.actualizarPorId(id, socioActualizado);

        // then
        assertNotNull(resultado);
        assertEquals("Los Krausse Updated", resultado.getSocio());
        assertEquals("El mirador", resultado.getPredio());
        assertEquals(20, resultado.getCapacidad());
        assertEquals(Estado.SUSPENDIDO, resultado.getEstado());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    @Test
    void actualizarPorId_retornaNullCuandoSocioNoEncontrado() {
        // given
        Long id = 999L;
        Socio socioActualizado = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Socio resultado = socioService.actualizarPorId(id, socioActualizado);

        // then
        assertNull(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).save(any());
    }

    @Test
    void eliminarSocio_retornaTrueCuandoSocioExisteYSeElimina() {
        // given
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));
        doNothing().when(socioRepository).deleteById(id);

        // when
        boolean resultado = socioService.eliminarSocio(id);

        // then
        assertTrue(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository).deleteById(id);
    }

    @Test
    void eliminarSocio_retornaFalseCuandoSocioNoEncontrado() {
        // given
        Long id = 999L;
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        boolean resultado = socioService.eliminarSocio(id);

        // then
        assertFalse(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).deleteById(anyLong());
    }

    @Test
    void eliminarSocio_relanzaExcepcionCuandoRepositorioFalla() {
        // given
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));
        doThrow(new RuntimeException("Error de BD")).when(socioRepository).deleteById(id);

        // when & then
        assertThrows(RuntimeException.class, () -> socioService.eliminarSocio(id));
        verify(socioRepository).findById(id);
        verify(socioRepository).deleteById(id);
    }

    @Test
    void actualizarParcial_actualizaCampoSocio() {
        // given
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse Modificado", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("socio", "Los Krausse Modificado");

        // when
        Socio resultado = socioService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Los Krausse Modificado", resultado.getSocio());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    @Test
    void actualizarParcial_actualizaCampoPredio() {
        // given
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "El mirador", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("predio", "El mirador");

        // when
        Socio resultado = socioService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("El mirador", resultado.getPredio());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    @Test
    void actualizarParcial_actualizaCampoCapacidad() {
        // given
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "Los abetos", 20, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("capacidad", 20);

        // when
        Socio resultado = socioService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(20, resultado.getCapacidad());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    @Test
    void actualizarParcial_actualizaCampoEstado() {
        // given
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.MANTENIMIENTO);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("estado", "MANTENIMIENTO");

        // when
        Socio resultado = socioService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(Estado.MANTENIMIENTO, resultado.getEstado());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    @Test
    void actualizarParcial_retornaNullCuandoSocioNoEncontrado() {
        // given
        Long id = 999L;
        Map<String, Object> campos = new HashMap<>();
        campos.put("socio", "Nombre Nuevo");
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Socio resultado = socioService.actualizarParcial(id, campos);

        // then
        assertNull(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).save(any());
    }
}
