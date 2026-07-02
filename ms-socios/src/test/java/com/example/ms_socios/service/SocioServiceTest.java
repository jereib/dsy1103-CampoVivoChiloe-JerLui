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
/**
 * Pruebas unitarias del servicio SocioService.
 * Verifica la lógica de negocio de registro, consulta, actualización y eliminación de socios.
 */
class SocioServiceTest {

    @Mock
    private SocioRepository socioRepository;

    @InjectMocks
    private SocioService socioService;

    /**
     * Verifica que listarSocios devuelva todos los socios del repositorio.
     */
    @Test
    void listarSocios_retornaTodosLosSocios() {
        List<Socio> sociosEsperados = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE),
                new Socio(2L, "Los Jackson", "Los avellanos", 10, Estado.SUSPENDIDO)
        );
        when(socioRepository.findAll()).thenReturn(sociosEsperados);

        List<Socio> resultado = socioService.listarSocios();

        assertEquals(2, resultado.size());
        assertEquals(sociosEsperados, resultado);
        verify(socioRepository).findAll();
    }

    /**
     * Verifica que buscarPorEstado filtre correctamente por el estado indicado.
     */
    @Test
    void buscarPorEstado_retornaSociosFiltradosPorEstado() {
        Estado estado = Estado.DISPONIBLE;
        List<Socio> sociosFiltrados = List.of(
                new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE)
        );
        when(socioRepository.findByEstado(estado)).thenReturn(sociosFiltrados);

        List<Socio> resultado = socioService.buscarPorEstado(estado);

        assertEquals(1, resultado.size());
        assertEquals(Estado.DISPONIBLE, resultado.get(0).getEstado());
        verify(socioRepository).findByEstado(estado);
    }

    /**
     * Verifica que buscarPorId devuelva el socio cuando existe en la BD.
     */
    @Test
    void buscarPorId_retornaSocioCuandoExiste() {
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));

        Socio resultado = socioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Los Krausse", resultado.getSocio());
        verify(socioRepository).findById(id);
    }

    /**
     * Verifica que buscarPorId devuelva null cuando el socio no existe.
     */
    @Test
    void buscarPorId_retornaNullCuandoNoExiste() {
        Long id = 999L;
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        Socio resultado = socioService.buscarPorId(id);

        assertNull(resultado);
        verify(socioRepository).findById(id);
    }

    /**
     * Verifica que guardarSocio guarde y retorne el socio si el nombre es único.
     */
    @Test
    void guardarSocio_guardaYRetornaCuandoNombreEsUnico() {
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(1L, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(false);
        when(socioRepository.save(socio)).thenReturn(socioGuardado);

        Socio resultado = socioService.guardarSocio(socio);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Los Krausse", resultado.getSocio());
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository).save(socio);
    }

    /**
     * Verifica que guardarSocio retorne null si el nombre ya está registrado.
     */
    @Test
    void guardarSocio_retornaNullCuandoNombreYaExiste() {
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(true);

        Socio resultado = socioService.guardarSocio(socio);

        assertNull(resultado);
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository, never()).save(any());
    }

    /**
     * Verifica que guardarSocio relance la excepción si el repositorio falla.
     */
    @Test
    void guardarSocio_relanzaExcepcionCuandoRepositorioFalla() {
        Socio socio = new Socio(null, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.existsBySocioIgnoreCase("Los Krausse")).thenReturn(false);
        when(socioRepository.save(socio)).thenThrow(new RuntimeException("Error de BD"));

        assertThrows(RuntimeException.class, () -> socioService.guardarSocio(socio));
        verify(socioRepository).existsBySocioIgnoreCase("Los Krausse");
        verify(socioRepository).save(socio);
    }

    /**
     * Verifica que actualizarPorId modifique todos los campos del socio existente.
     */
    @Test
    void actualizarPorId_actualizaCuandoSocioExiste() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioActualizado = new Socio(id, "Los Krausse Updated", "El mirador", 20, Estado.SUSPENDIDO);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioActualizado);

        Socio resultado = socioService.actualizarPorId(id, socioActualizado);

        assertNotNull(resultado);
        assertEquals("Los Krausse Updated", resultado.getSocio());
        assertEquals("El mirador", resultado.getPredio());
        assertEquals(20, resultado.getCapacidad());
        assertEquals(Estado.SUSPENDIDO, resultado.getEstado());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarPorId retorne null si el socio no existe.
     */
    @Test
    void actualizarPorId_retornaNullCuandoSocioNoEncontrado() {
        Long id = 999L;
        Socio socioActualizado = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        Socio resultado = socioService.actualizarPorId(id, socioActualizado);

        assertNull(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).save(any());
    }

    /**
     * Verifica que actualizarPorId relance la excepción si el repositorio falla.
     */
    @Test
    void actualizarPorId_relanzaExcepcionCuandoRepositorioFalla() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioActualizado = new Socio(id, "Los Krausse Updated", "El mirador", 20, Estado.SUSPENDIDO);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenThrow(new RuntimeException("Error de BD"));

        assertThrows(RuntimeException.class, () -> socioService.actualizarPorId(id, socioActualizado));
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que eliminarSocio retorne true cuando el socio existe y se elimina.
     */
    @Test
    void eliminarSocio_retornaTrueCuandoSocioExisteYSeElimina() {
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));
        doNothing().when(socioRepository).deleteById(id);

        boolean resultado = socioService.eliminarSocio(id);

        assertTrue(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository).deleteById(id);
    }

    /**
     * Verifica que eliminarSocio retorne false si el socio no existe.
     */
    @Test
    void eliminarSocio_retornaFalseCuandoSocioNoEncontrado() {
        Long id = 999L;
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        boolean resultado = socioService.eliminarSocio(id);

        assertFalse(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).deleteById(anyLong());
    }

    /**
     * Verifica que eliminarSocio relance la excepción si el repositorio falla.
     */
    @Test
    void eliminarSocio_relanzaExcepcionCuandoRepositorioFalla() {
        Long id = 1L;
        Socio socio = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socio));
        doThrow(new RuntimeException("Error de BD")).when(socioRepository).deleteById(id);

        assertThrows(RuntimeException.class, () -> socioService.eliminarSocio(id));
        verify(socioRepository).findById(id);
        verify(socioRepository).deleteById(id);
    }

    /**
     * Verifica que actualizarParcial modifique el campo socio correctamente.
     */
    @Test
    void actualizarParcial_actualizaCampoSocio() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse Modificado", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("socio", "Los Krausse Modificado");

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("Los Krausse Modificado", resultado.getSocio());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarParcial modifique el campo predio correctamente.
     */
    @Test
    void actualizarParcial_actualizaCampoPredio() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "El mirador", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("predio", "El mirador");

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("El mirador", resultado.getPredio());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarParcial modifique el campo capacidad correctamente.
     */
    @Test
    void actualizarParcial_actualizaCampoCapacidad() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "Los abetos", 20, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("capacidad", 20);

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals(20, resultado.getCapacidad());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarParcial modifique el campo estado correctamente.
     */
    @Test
    void actualizarParcial_actualizaCampoEstado() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        Socio socioGuardado = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.MANTENIMIENTO);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenReturn(socioGuardado);

        Map<String, Object> campos = new HashMap<>();
        campos.put("estado", "MANTENIMIENTO");

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals(Estado.MANTENIMIENTO, resultado.getEstado());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarParcial retorne null si el socio no existe.
     */
    @Test
    void actualizarParcial_retornaNullCuandoSocioNoEncontrado() {
        Long id = 999L;
        Map<String, Object> campos = new HashMap<>();
        campos.put("socio", "Nombre Nuevo");
        when(socioRepository.findById(id)).thenReturn(Optional.empty());

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNull(resultado);
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).save(any());
    }

    /**
     * Verifica que actualizarParcial ignore campos desconocidos sin lanzar excepción.
     */
    @Test
    void actualizarParcial_campoDesconocido_noLanzaExcepcion() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("campoInexistente", "valor");

        Socio resultado = socioService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("Los Krausse", resultado.getSocio());
        assertEquals("Los abetos", resultado.getPredio());
        assertEquals(15, resultado.getCapacidad());
        assertEquals(Estado.DISPONIBLE, resultado.getEstado());
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }

    /**
     * Verifica que actualizarParcial lance IllegalArgumentException si el estado no es válido.
     */
    @Test
    void actualizarParcial_lanzaExcepcion_cuandoEstadoNoValido() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));

        Map<String, Object> campos = new HashMap<>();
        campos.put("estado", "ESTADO_INEXISTENTE");

        assertThrows(IllegalArgumentException.class, () -> socioService.actualizarParcial(id, campos));
        verify(socioRepository).findById(id);
        verify(socioRepository, never()).save(any());
    }

    /**
     * Verifica que actualizarParcial relance la excepción si el repositorio falla.
     */
    @Test
    void actualizarParcial_relanzaExcepcion_cuandoRepositorioFalla() {
        Long id = 1L;
        Socio socioExistente = new Socio(id, "Los Krausse", "Los abetos", 15, Estado.DISPONIBLE);
        when(socioRepository.findById(id)).thenReturn(Optional.of(socioExistente));
        when(socioRepository.save(any(Socio.class))).thenThrow(new RuntimeException("Error de BD"));

        Map<String, Object> campos = new HashMap<>();
        campos.put("socio", "Nuevo Nombre");

        assertThrows(RuntimeException.class, () -> socioService.actualizarParcial(id, campos));
        verify(socioRepository).findById(id);
        verify(socioRepository).save(socioExistente);
    }
}
