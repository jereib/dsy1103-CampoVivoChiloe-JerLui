package com.example.ms_liquidaciones.service;

import com.example.ms_liquidaciones.client.FondoClient;
import com.example.ms_liquidaciones.model.Liquidacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LiquidacionServiceTest {

    @Mock
    private FondoClient fondoClient;

    @InjectMocks
    private LiquidacionService liquidacionService;

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
}
