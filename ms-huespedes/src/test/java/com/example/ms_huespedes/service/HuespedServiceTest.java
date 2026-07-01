package com.example.ms_huespedes.service;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.repository.HuespedRepositorio;
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
class HuespedServiceTest {

    @Mock
    private HuespedRepositorio huespedRepositorio;

    @InjectMocks
    private HuespedService huespedService;

    @Test
    void listarHuespedes_retornaTodos() {
        // given
        List<Huesped> huespedes = List.of(
                new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno que le gusta comer completos", "Se hospedó anteriormente en 2024", "benja123@gmail.com"),
                new Huesped(2L, "Maria Perez", "12345678-9", 30, "turista", "Primera vez", "maria@gmail.com")
        );
        when(huespedRepositorio.findAll()).thenReturn(huespedes);

        // when
        List<Huesped> resultado = huespedService.listarHuespedes();

        // then
        assertEquals(2, resultado.size());
        verify(huespedRepositorio).findAll();
    }

    @Test
    void buscarPorId_retornaHuespedCuandoExiste() {
        // given
        Long id = 1L;
        Huesped huesped = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huesped));

        // when
        Huesped resultado = huespedService.buscarPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedRepositorio).findById(id);
    }

    @Test
    void buscarPorId_retornaNullCuandoNoExiste() {
        // given
        Long id = 999L;
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        // when
        Huesped resultado = huespedService.buscarPorId(id);

        // then
        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
    }

    @Test
    void guardarHuesped_guardaCuandoNombreEsUnico() {
        // given
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        Huesped huespedGuardado = new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(false);
        when(huespedRepositorio.save(huesped)).thenReturn(huespedGuardado);

        // when
        Huesped resultado = huespedService.guardarHuesped(huesped);

        // then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio).save(huesped);
    }

    @Test
    void guardarHuesped_retornaNullCuandoNombreDuplicado() {
        // given
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(true);

        // when
        Huesped resultado = huespedService.guardarHuesped(huesped);

        // then
        assertNull(resultado);
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio, never()).save(any());
    }

    @Test
    void guardarHuesped_relanzaExcepcionCuandoRepositorioFalla() {
        // given
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(false);
        when(huespedRepositorio.save(huesped)).thenThrow(new RuntimeException("Error de BD"));

        // when & then
        assertThrows(RuntimeException.class, () -> huespedService.guardarHuesped(huesped));
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio).save(huesped);
    }

    @Test
    void actualizarPorId_actualizaCuandoHuespedExiste() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        Huesped huespedActualizado = new Huesped(id, "Benjamin agüero update", "21659428-2", 22, "turista", "nuevo historial", "nuevo@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenReturn(huespedActualizado);

        // when
        Huesped resultado = huespedService.actualizarPorId(id, huespedActualizado);

        // then
        assertNotNull(resultado);
        assertEquals("Benjamin agüero update", resultado.getNombreCompleto());
        assertEquals(22, resultado.getEdad());
        assertEquals("turista", resultado.getPerfil());
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarPorId_retornaNullCuandoNoEncontrado() {
        // given
        Long id = 999L;
        Huesped huespedActualizado = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        // when
        Huesped resultado = huespedService.actualizarPorId(id, huespedActualizado);

        // then
        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).save(any());
    }

    @Test
    void eliminarHuesped_retornaTrueCuandoExiste() {
        // given
        Long id = 1L;
        Huesped huesped = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huesped));
        doNothing().when(huespedRepositorio).deleteById(id);

        // when
        boolean resultado = huespedService.eliminarHuesped(id);

        // then
        assertTrue(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).deleteById(id);
    }

    @Test
    void eliminarHuesped_retornaFalseCuandoNoEncontrado() {
        // given
        Long id = 999L;
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        // when
        boolean resultado = huespedService.eliminarHuesped(id);

        // then
        assertFalse(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).deleteById(anyLong());
    }

    @Test
    void actualizarParcial_actualizaNombreCompleto() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreCompleto", "Benjamin agüero modificado");

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("Benjamin agüero modificado", resultado.getNombreCompleto());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_actualizaRut() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("rut", "11111111-1");

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("11111111-1", resultado.getRut());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_actualizaEdad() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("edad", 25);

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals(25, resultado.getEdad());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_actualizaPerfil() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("perfil", "turista internacional");

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("turista internacional", resultado.getPerfil());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_actualizaHistorial() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("historial", "nuevo historial detallado");

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("nuevo historial detallado", resultado.getHistorial());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_actualizaCorreo() {
        // given
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("correo", "nuevo.correo@gmail.com");

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNotNull(resultado);
        assertEquals("nuevo.correo@gmail.com", resultado.getCorreo());
        verify(huespedRepositorio).save(huespedExistente);
    }

    @Test
    void actualizarParcial_retornaNullCuandoNoEncontrado() {
        // given
        Long id = 999L;
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreCompleto", "Nombre Nuevo");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        // when
        Huesped resultado = huespedService.actualizarParcial(id, campos);

        // then
        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).save(any());
    }
}
