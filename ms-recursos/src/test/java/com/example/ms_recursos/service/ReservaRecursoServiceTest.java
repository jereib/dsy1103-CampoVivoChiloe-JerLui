package com.example.ms_recursos.service;

import com.example.ms_recursos.client.FondoClient;
import com.example.ms_recursos.model.ReservaRecurso;
import com.example.ms_recursos.repository.ReservaRecursoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaRecursoServiceTest {

    @Mock
    private FondoClient fondoClient;

    @Mock
    private ReservaRecursoRepository reservaRecursoRepository;

    @InjectMocks
    private ReservaRecursoService reservaRecursoService;

    @Test
    void guardar_debeLanzarExcepcion_cuandoSocioTieneDeuda() {
        // Given: un socio con deuda activa
        Long socioId = 1L;
        ReservaRecurso reserva = new ReservaRecurso(socioId, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        when(fondoClient.tieneDeudaActiva(socioId)).thenReturn(true);

        // When: se intenta guardar la reserva
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaRecursoService.guardar(reserva);
        });

        // Then: se lanza excepción y no se guarda
        assertEquals("El socio tiene deuda, no puede reservar recurso", exception.getMessage());
        verify(reservaRecursoRepository, never()).save(any());
    }

    @Test
    void guardar_debeGuardarReserva_cuandoSocioNoTieneDeuda() {
        // Given: un socio sin deuda
        Long socioId = 2L;
        ReservaRecurso reserva = new ReservaRecurso(socioId, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        ReservaRecurso reservaGuardada = new ReservaRecurso(socioId, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");

        when(fondoClient.tieneDeudaActiva(socioId)).thenReturn(false);
        when(reservaRecursoRepository.save(any())).thenReturn(reservaGuardada);

        // When: se guarda la reserva
        ReservaRecurso resultado = reservaRecursoService.guardar(reserva);

        // Then: se guarda exitosamente
        assertNotNull(resultado);
        assertEquals("Tractor", resultado.getNombreRecurso());
        assertEquals("PENDIENTE", resultado.getEstado());
        verify(reservaRecursoRepository, times(1)).save(reserva);
    }

    @Test
    void listar_debeRetornarLista() {
        when(reservaRecursoRepository.findAll()).thenReturn(List.of());

        List<ReservaRecurso> resultado = reservaRecursoService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void buscarPorId_debeRetornarReserva_cuandoExiste() {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        r.setId(1L);
        when(reservaRecursoRepository.findById(1L)).thenReturn(java.util.Optional.of(r));

        ReservaRecurso resultado = reservaRecursoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Tractor", resultado.getNombreRecurso());
    }

    @Test
    void buscarPorId_debeRetornarNull_cuandoNoExiste() {
        when(reservaRecursoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        ReservaRecurso resultado = reservaRecursoService.buscarPorId(99L);

        assertNull(resultado);
    }

    @Test
    void eliminar_debeRetornarTrue_cuandoExiste() {
        ReservaRecurso r = new ReservaRecurso(1L, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        r.setId(1L);
        when(reservaRecursoRepository.findById(1L)).thenReturn(java.util.Optional.of(r));

        boolean resultado = reservaRecursoService.eliminar(1L);

        assertTrue(resultado);
        verify(reservaRecursoRepository).delete(r);
    }

    @Test
    void eliminar_debeRetornarFalse_cuandoNoExiste() {
        when(reservaRecursoRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        boolean resultado = reservaRecursoService.eliminar(99L);

        assertFalse(resultado);
        verify(reservaRecursoRepository, never()).delete(any());
    }

    @Test
    void guardar_debeLanzarExcepcion_cuandoFondoNoResponde() {
        // Given: ms-fondo no responde
        Long socioId = 3L;
        ReservaRecurso reserva = new ReservaRecurso(socioId, "Tractor", "2026-07-01", "2026-07-03", "PENDIENTE");
        when(fondoClient.tieneDeudaActiva(socioId)).thenThrow(new RuntimeException("Servicio no disponible"));

        // When: se intenta guardar la reserva
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaRecursoService.guardar(reserva);
        });

        // Then: se lanza excepción de comunicación
        assertEquals("No se pudo validar la deuda del socio. Intente más tarde.", exception.getMessage());
        verify(reservaRecursoRepository, never()).save(any());
    }
}
