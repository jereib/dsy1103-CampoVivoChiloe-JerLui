package com.example.ms_fondo.service;

import com.example.ms_fondo.model.DeudaSocio;
import com.example.ms_fondo.repository.DeudaSocioRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeudaSocioServiceTest {

    @Mock
    private DeudaSocioRepository deudaSocioRepository;

    @InjectMocks
    private DeudaSocioService deudaSocioService;

    @Test
    void listar_retornaTodasLasDeudas() {
        // given
        List<DeudaSocio> deudas = List.of(
                new DeudaSocio(1L, 30000, "ACTIVA"),
                new DeudaSocio(1L, 15000, "PAGADA")
        );
        when(deudaSocioRepository.findAll()).thenReturn(deudas);

        // when
        List<DeudaSocio> resultado = deudaSocioService.listar();

        // then
        assertEquals(2, resultado.size());
        verify(deudaSocioRepository).findAll();
    }

    @Test
    void buscarPorId_retornaDeudaSocioCuandoExiste() {
        // given
        Long id = 1L;
        DeudaSocio deuda = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.of(deuda));

        // when
        DeudaSocio resultado = deudaSocioService.buscarPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(30000, resultado.getMonto());
        verify(deudaSocioRepository).findById(id);
    }

    @Test
    void buscarPorId_retornaNullCuandoNoExiste() {
        // given
        Long id = 999L;
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        DeudaSocio resultado = deudaSocioService.buscarPorId(id);

        // then
        assertNull(resultado);
        verify(deudaSocioRepository).findById(id);
    }

    @Test
    void guardar_guardaYRetorna() {
        // given
        DeudaSocio deuda = new DeudaSocio(1L, 30000, "ACTIVA");
        DeudaSocio deudaGuardada = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioRepository.save(deuda)).thenReturn(deudaGuardada);

        // when
        DeudaSocio resultado = deudaSocioService.guardar(deuda);

        // then
        assertNotNull(resultado);
        assertEquals(30000, resultado.getMonto());
        verify(deudaSocioRepository).save(deuda);
    }

    @Test
    void actualizar_actualizaCuandoExiste() {
        // given
        Long id = 1L;
        DeudaSocio deudaExistente = new DeudaSocio(1L, 30000, "ACTIVA");
        DeudaSocio deudaActualizada = new DeudaSocio(2L, 50000, "PAGADA");
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.of(deudaExistente));
        when(deudaSocioRepository.save(any(DeudaSocio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        DeudaSocio resultado = deudaSocioService.actualizar(id, deudaActualizada);

        // then
        assertNotNull(resultado);
        assertEquals(2L, resultado.getSocioId());
        assertEquals(50000, resultado.getMonto());
        assertEquals("PAGADA", resultado.getEstado());
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository).save(deudaExistente);
    }

    @Test
    void actualizar_retornaNullCuandoNoExiste() {
        // given
        Long id = 999L;
        DeudaSocio deudaActualizada = new DeudaSocio(1L, 50000, "PAGADA");
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        DeudaSocio resultado = deudaSocioService.actualizar(id, deudaActualizada);

        // then
        assertNull(resultado);
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository, never()).save(any());
    }

    @Test
    void eliminar_retornaTrueCuandoExiste() {
        // given
        Long id = 1L;
        DeudaSocio deuda = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.of(deuda));
        doNothing().when(deudaSocioRepository).delete(deuda);

        // when
        boolean resultado = deudaSocioService.eliminar(id);

        // then
        assertTrue(resultado);
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository).delete(deuda);
    }

    @Test
    void eliminar_retornaFalseCuandoNoExiste() {
        // given
        Long id = 999L;
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        boolean resultado = deudaSocioService.eliminar(id);

        // then
        assertFalse(resultado);
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository, never()).delete(any());
    }

    @Test
    void buscarPorSocio_retornaListaPorSocioId() {
        // given
        Long socioId = 1L;
        List<DeudaSocio> deudas = List.of(
                new DeudaSocio(socioId, 30000, "ACTIVA"),
                new DeudaSocio(socioId, 15000, "PAGADA")
        );
        when(deudaSocioRepository.findBySocioId(socioId)).thenReturn(deudas);

        // when
        List<DeudaSocio> resultado = deudaSocioService.buscarPorSocio(socioId);

        // then
        assertEquals(2, resultado.size());
        verify(deudaSocioRepository).findBySocioId(socioId);
    }

    @Test
    void tieneDeudaActiva_retornaTrueCuandoHayDeudasActivas() {
        // given
        Long socioId = 1L;
        List<DeudaSocio> deudasActivas = List.of(new DeudaSocio(socioId, 30000, "ACTIVA"));
        when(deudaSocioRepository.findBySocioIdAndEstado(socioId, "ACTIVA")).thenReturn(deudasActivas);

        // when
        boolean resultado = deudaSocioService.tieneDeudaActiva(socioId);

        // then
        assertTrue(resultado);
        verify(deudaSocioRepository).findBySocioIdAndEstado(socioId, "ACTIVA");
    }

    @Test
    void tieneDeudaActiva_retornaFalseCuandoNoHayDeudasActivas() {
        // given
        Long socioId = 1L;
        when(deudaSocioRepository.findBySocioIdAndEstado(socioId, "ACTIVA")).thenReturn(List.of());

        // when
        boolean resultado = deudaSocioService.tieneDeudaActiva(socioId);

        // then
        assertFalse(resultado);
        verify(deudaSocioRepository).findBySocioIdAndEstado(socioId, "ACTIVA");
    }

    @Test
    void actualizarParcial_actualizaCampoMonto() {
        // given
        Long id = 1L;
        DeudaSocio deudaExistente = new DeudaSocio(1L, 30000, "ACTIVA");
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.of(deudaExistente));
        when(deudaSocioRepository.save(any(DeudaSocio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("monto", 50000.0);

        // when
        DeudaSocio resultado = deudaSocioService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(50000, resultado.getMonto());
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository).save(deudaExistente);
    }

    @Test
    void actualizarParcial_retornaNullCuandoNoExiste() {
        // given
        Long id = 999L;
        Map<String, Object> campos = new HashMap<>();
        campos.put("monto", 50000.0);
        when(deudaSocioRepository.findById(id)).thenReturn(Optional.empty());

        // when
        DeudaSocio resultado = deudaSocioService.actualizarParcial(id, campos);

        // then
        assertNull(resultado);
        verify(deudaSocioRepository).findById(id);
        verify(deudaSocioRepository, never()).save(any());
    }
}
