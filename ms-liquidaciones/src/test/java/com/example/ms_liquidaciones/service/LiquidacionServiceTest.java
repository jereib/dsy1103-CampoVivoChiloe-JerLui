package com.example.ms_liquidaciones.service;

import com.example.ms_liquidaciones.client.FondoClient;
import com.example.ms_liquidaciones.model.Liquidacion;
import com.example.ms_liquidaciones.repository.LiquidacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio LiquidacionService.
 * Verifica la lógica de negocio usando mocks para el repositorio y el cliente Feign.
 */
@ExtendWith(MockitoExtension.class)
class LiquidacionServiceTest {

    @Mock
    private FondoClient fondoClient;

    @Mock
    private LiquidacionRepository liquidacionRepository;

    @InjectMocks
    private LiquidacionService liquidacionService;

    /**
     * Verifica que la liquidación descuente la deuda cuando el socio debe.
     */
    @Test
    void generarLiquidacion_debeCalcularConDeuda_cuandoSocioTieneDeudaActiva() {
        // Given: un socio con deuda activa
        Long socioId = 1L;
        when(fondoClient.tieneDeudaActiva(socioId)).thenReturn(true);

        // When: se genera la liquidación
        Liquidacion liquidacion = liquidacionService.generarLiquidacion(socioId);

        // Then: los valores reflejan la deuda
        assertNotNull(liquidacion);
        assertEquals(socioId, liquidacion.getSocioId());
        assertEquals(200000, liquidacion.getIngresos(), 0.001);
        assertEquals(50000, liquidacion.getDeuda(), 0.001);
        assertEquals(150000, liquidacion.getTotal(), 0.001);
    }

    /**
     * Verifica que la liquidación no descuente deuda cuando el socio no debe.
     */
    @Test
    void generarLiquidacion_debeCalcularSinDeuda_cuandoSocioNoTieneDeuda() {
        // Given: un socio sin deuda
        Long socioId = 2L;
        when(fondoClient.tieneDeudaActiva(socioId)).thenReturn(false);

        // When: se genera la liquidación
        Liquidacion liquidacion = liquidacionService.generarLiquidacion(socioId);

        // Then: los valores reflejan que no hay deuda
        assertNotNull(liquidacion);
        assertEquals(socioId, liquidacion.getSocioId());
        assertEquals(200000, liquidacion.getIngresos(), 0.001);
        assertEquals(0, liquidacion.getDeuda(), 0.001);
        assertEquals(200000, liquidacion.getTotal(), 0.001);
    }

    /**
     * Verifica que se lance excepción cuando ms-fondo no responde.
     */
    @Test
    void generarLiquidacion_debeLanzarExcepcion_cuandoFondoNoResponde() {
        // Given: ms-fondo no responde
        Long socioId = 3L;
        when(fondoClient.tieneDeudaActiva(socioId)).thenThrow(new RuntimeException("Servicio no disponible"));

        // When: se intenta generar la liquidación
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            liquidacionService.generarLiquidacion(socioId);
        });

        // Then: se lanza excepción de comunicación
        assertEquals("No se pudo obtener el estado de deuda del socio.", exception.getMessage());
    }

    /**
     * Verifica que listar retorne una lista (posiblemente vacía).
     */
    @Test
    void listar_debeRetornarLista() {
        when(liquidacionRepository.findAll()).thenReturn(List.of());

        List<Liquidacion> resultado = liquidacionService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    /**
     * Verifica que guardar persista y retorne la liquidación.
     */
    @Test
    void guardar_debeGuardarLiquidacion() {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        when(liquidacionRepository.save(l)).thenReturn(l);

        Liquidacion resultado = liquidacionService.guardar(l);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getSocioId());
        verify(liquidacionRepository).save(l);
    }

    /**
     * Verifica que buscarPorId retorne la liquidación cuando existe.
     */
    @Test
    void buscarPorId_debeRetornarLiquidacion_cuandoExiste() {
        Liquidacion l = new Liquidacion(1L, 200000, 50000, 150000);
        l.setId(1L);
        when(liquidacionRepository.findById(1L)).thenReturn(Optional.of(l));

        Liquidacion resultado = liquidacionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    /**
     * Verifica que buscarPorId retorne null cuando no existe.
     */
    @Test
    void buscarPorId_debeRetornarNull_cuandoNoExiste() {
        when(liquidacionRepository.findById(99L)).thenReturn(Optional.empty());

        Liquidacion resultado = liquidacionService.buscarPorId(99L);

        assertNull(resultado);
    }

    /**
     * Verifica que actualizar retorne null cuando la liquidación no existe.
     */
    @Test
    void actualizar_debeRetornarNull_cuandoNoExiste() {
        when(liquidacionRepository.findById(99L)).thenReturn(Optional.empty());

        Liquidacion resultado = liquidacionService.actualizar(99L, new Liquidacion());

        assertNull(resultado);
    }

    /**
     * Verifica que actualizar modifique los campos correctamente cuando existe.
     */
    @Test
    void actualizar_debeActualizar_cuandoExiste() {
        Liquidacion existente = new Liquidacion(1L, 200000, 50000, 150000);
        existente.setId(1L);
        Liquidacion datos = new Liquidacion(1L, 300000, 0, 300000);

        when(liquidacionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(liquidacionRepository.save(existente)).thenReturn(existente);

        Liquidacion resultado = liquidacionService.actualizar(1L, datos);

        assertNotNull(resultado);
        assertEquals(300000, resultado.getIngresos(), 0.001);
        assertEquals(0, resultado.getDeuda(), 0.001);
        assertEquals(300000, resultado.getTotal(), 0.001);
    }

    /**
     * Verifica que eliminar retorne true cuando la liquidación existe.
     */
    @Test
    void eliminar_debeRetornarTrue_cuandoExiste() {
        when(liquidacionRepository.existsById(1L)).thenReturn(true);

        boolean resultado = liquidacionService.eliminar(1L);

        assertTrue(resultado);
        verify(liquidacionRepository).deleteById(1L);
    }

    /**
     * Verifica que eliminar retorne false cuando la liquidación no existe.
     */
    @Test
    void eliminar_debeRetornarFalse_cuandoNoExiste() {
        when(liquidacionRepository.existsById(99L)).thenReturn(false);

        boolean resultado = liquidacionService.eliminar(99L);

        assertFalse(resultado);
        verify(liquidacionRepository, never()).deleteById(any());
    }
}
