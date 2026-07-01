package com.example.ms_actividades.service;

import com.example.ms_actividades.client.SocioClient;
import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.dto.SocioDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.repository.ActividadesRepositorio;
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
class ActividadesServiceTest {

    @Mock
    private ActividadesRepositorio actividadRepository;

    @Mock
    private SocioClient socioClient;

    @InjectMocks
    private ActividadesService actividadesService;

    @Test
    void obtenerActividad_retornaDTO_cuandoActividadYSocioExisten() {
        // given
        Long id = 1L;
        ActividadModel actividad = new ActividadModel(id, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);
        SocioDTO socio = new SocioDTO(1L, "Los Jackson");

        when(actividadRepository.findById(id)).thenReturn(Optional.of(actividad));
        when(socioClient.obtenerSocio(1L)).thenReturn(socio);

        // when
        ActividadesResponseDTO resultado = actividadesService.obtenerActividad(id);

        // then
        assertNotNull(resultado);
        assertEquals("Taller de pintura", resultado.getNombreActividad());
        assertEquals("Actividad recreativa", resultado.getDescripcion());
        assertEquals("Sábado 15:00", resultado.getCalendario());
        assertEquals("Los Jackson", resultado.getSocio());
        verify(actividadRepository).findById(id);
        verify(socioClient).obtenerSocio(1L);
    }

    @Test
    void obtenerActividad_lanzaExcepcion_cuandoActividadNoEncontrada() {
        // given
        Long id = 999L;
        when(actividadRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.obtenerActividad(id));
        verify(actividadRepository).findById(id);
        verify(socioClient, never()).obtenerSocio(anyLong());
    }

    @Test
    void obtenerActividad_lanzaExcepcion_cuandoFeignFalla() {
        // given
        Long id = 1L;
        ActividadModel actividad = new ActividadModel(id, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);

        when(actividadRepository.findById(id)).thenReturn(Optional.of(actividad));
        when(socioClient.obtenerSocio(1L)).thenThrow(new RuntimeException("Error de comunicación"));

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.obtenerActividad(id));
        verify(actividadRepository).findById(id);
        verify(socioClient).obtenerSocio(1L);
    }

    @Test
    void crearActividad_guardaYRetornaActividad_cuandoExitoso() {
        // given
        ActividadModel actividad = new ActividadModel(null, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);
        ActividadModel actividadGuardada = new ActividadModel(1L, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);

        when(actividadRepository.save(actividad)).thenReturn(actividadGuardada);

        // when
        ActividadModel resultado = actividadesService.crearActividad(actividad);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Taller de pintura", resultado.getNombreActividad());
        verify(actividadRepository).save(actividad);
    }

    @Test
    void crearActividad_relanzaExcepcion_cuandoError() {
        // given
        ActividadModel actividad = new ActividadModel(null, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);

        when(actividadRepository.save(actividad)).thenThrow(new RuntimeException("Error de BD"));

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.crearActividad(actividad));
        verify(actividadRepository).save(actividad);
    }

    @Test
    void listarActividades_retornaListaDTOs_cuandoExisten() {
        // given
        List<ActividadModel> actividades = List.of(
                new ActividadModel(1L, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L),
                new ActividadModel(2L, "Clase de yoga", "Relajación", "Domingo 10:00", 2L)
        );

        SocioDTO socio1 = new SocioDTO(1L, "Los Jackson");
        SocioDTO socio2 = new SocioDTO(2L, "Los Krausse");

        when(actividadRepository.findAll()).thenReturn(actividades);
        when(socioClient.obtenerSocio(1L)).thenReturn(socio1);
        when(socioClient.obtenerSocio(2L)).thenReturn(socio2);

        // when
        List<ActividadesResponseDTO> resultado = actividadesService.listarActividades();

        // then
        assertEquals(2, resultado.size());
        assertEquals("Taller de pintura", resultado.get(0).getNombreActividad());
        assertEquals("Los Jackson", resultado.get(0).getSocio());
        assertEquals("Clase de yoga", resultado.get(1).getNombreActividad());
        assertEquals("Los Krausse", resultado.get(1).getSocio());
        verify(actividadRepository).findAll();
        verify(socioClient).obtenerSocio(1L);
        verify(socioClient).obtenerSocio(2L);
    }

    @Test
    void listarActividades_continua_cuandoFeignFallaParaUnaActividad() {
        // given
        List<ActividadModel> actividades = List.of(
                new ActividadModel(1L, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L),
                new ActividadModel(2L, "Clase de yoga", "Relajación", "Domingo 10:00", 2L)
        );

        SocioDTO socio1 = new SocioDTO(1L, "Los Jackson");

        when(actividadRepository.findAll()).thenReturn(actividades);
        when(socioClient.obtenerSocio(1L)).thenReturn(socio1);
        when(socioClient.obtenerSocio(2L)).thenThrow(new RuntimeException("Error de comunicación"));

        // when
        List<ActividadesResponseDTO> resultado = actividadesService.listarActividades();

        // then
        assertEquals(1, resultado.size());
        assertEquals("Taller de pintura", resultado.get(0).getNombreActividad());
        assertEquals("Los Jackson", resultado.get(0).getSocio());
        verify(actividadRepository).findAll();
        verify(socioClient).obtenerSocio(1L);
        verify(socioClient).obtenerSocio(2L);
    }

    @Test
    void actualizarActividad_actualiza_cuandoExiste() {
        // given
        Long id = 1L;
        ActividadModel actividadExistente = new ActividadModel(id, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);
        ActividadModel actividadActualizada = new ActividadModel(id, "Taller de acuarela", "Pintura con acuarela", "Sábado 16:00", 2L);

        when(actividadRepository.findById(id)).thenReturn(Optional.of(actividadExistente));
        when(actividadRepository.save(any(ActividadModel.class))).thenReturn(actividadExistente);

        // when
        ActividadModel resultado = actividadesService.actualizarActividad(id, actividadActualizada);

        // then
        assertNotNull(resultado);
        assertEquals("Taller de acuarela", resultado.getNombreActividad());
        assertEquals("Pintura con acuarela", resultado.getDescripcion());
        assertEquals("Sábado 16:00", resultado.getCalendario());
        assertEquals(2L, resultado.getSocioId());
        verify(actividadRepository).findById(id);
        verify(actividadRepository).save(actividadExistente);
    }

    @Test
    void actualizarActividad_lanzaExcepcion_cuandoNoEncontrada() {
        // given
        Long id = 999L;
        ActividadModel actividadActualizada = new ActividadModel(id, "Taller de acuarela", "Pintura", "Sábado 16:00", 2L);

        when(actividadRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.actualizarActividad(id, actividadActualizada));
        verify(actividadRepository).findById(id);
        verify(actividadRepository, never()).save(any());
    }

    @Test
    void eliminarActividad_elimina_cuandoExiste() {
        // given
        Long id = 1L;
        ActividadModel actividad = new ActividadModel(id, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);

        when(actividadRepository.findById(id)).thenReturn(Optional.of(actividad));

        // when
        actividadesService.eliminarActividad(id);

        // then
        verify(actividadRepository).findById(id);
        verify(actividadRepository).delete(actividad);
    }

    @Test
    void eliminarActividad_lanzaExcepcion_cuandoNoEncontrada() {
        // given
        Long id = 999L;
        when(actividadRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.eliminarActividad(id));
        verify(actividadRepository).findById(id);
        verify(actividadRepository, never()).delete(any());
    }

    @Test
    void actualizarParcial_actualizaNombreActividad_cuandoExiste() {
        // given
        Long id = 1L;
        ActividadModel actividadExistente = new ActividadModel(id, "Taller de pintura", "Actividad recreativa", "Sábado 15:00", 1L);

        when(actividadRepository.findById(id)).thenReturn(Optional.of(actividadExistente));
        when(actividadRepository.save(any(ActividadModel.class))).thenReturn(actividadExistente);

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreActividad", "Taller de acuarela");

        // when
        ActividadModel resultado = actividadesService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Taller de acuarela", resultado.getNombreActividad());
        verify(actividadRepository).findById(id);
        verify(actividadRepository).save(actividadExistente);
    }

    @Test
    void actualizarParcial_lanzaExcepcion_cuandoNoEncontrada() {
        // given
        Long id = 999L;
        when(actividadRepository.findById(id)).thenReturn(Optional.empty());

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreActividad", "Taller de acuarela");

        // when & then
        assertThrows(RuntimeException.class, () -> actividadesService.actualizarParcial(id, campos));
        verify(actividadRepository).findById(id);
        verify(actividadRepository, never()).save(any());
    }
}
