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

/**
 * Pruebas unitarias del servicio HuespedService.
 * Utiliza mocks del repositorio para verificar la lógica de negocio.
 */
@ExtendWith(MockitoExtension.class)
class HuespedServiceTest {

    @Mock
    private HuespedRepositorio huespedRepositorio;

    @InjectMocks
    private HuespedService huespedService;

    /**
     * Verifica que listarHuespedes retorna todos los huéspedes.
     */
    @Test
    void listarHuespedes_retornaTodos() {
        List<Huesped> huespedes = List.of(
                new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno que le gusta comer completos", "Se hospedó anteriormente en 2024", "benja123@gmail.com"),
                new Huesped(2L, "Maria Perez", "12345678-9", 30, "turista", "Primera vez", "maria@gmail.com")
        );
        when(huespedRepositorio.findAll()).thenReturn(huespedes);

        List<Huesped> resultado = huespedService.listarHuespedes();

        assertEquals(2, resultado.size());
        verify(huespedRepositorio).findAll();
    }

    /**
     * Verifica que buscarPorId retorna el huésped cuando existe.
     */
    @Test
    void buscarPorId_retornaHuespedCuandoExiste() {
        Long id = 1L;
        Huesped huesped = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huesped));

        Huesped resultado = huespedService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedRepositorio).findById(id);
    }

    /**
     * Verifica que buscarPorId retorna null cuando el huésped no existe.
     */
    @Test
    void buscarPorId_retornaNullCuandoNoExiste() {
        Long id = 999L;
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        Huesped resultado = huespedService.buscarPorId(id);

        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
    }

    /**
     * Verifica que guardarHuesped guarda cuando el nombre no está duplicado.
     */
    @Test
    void guardarHuesped_guardaCuandoNombreEsUnico() {
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        Huesped huespedGuardado = new Huesped(1L, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(false);
        when(huespedRepositorio.save(huesped)).thenReturn(huespedGuardado);

        Huesped resultado = huespedService.guardarHuesped(huesped);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio).save(huesped);
    }

    /**
     * Verifica que guardarHuesped retorna null si el nombre ya existe.
     */
    @Test
    void guardarHuesped_retornaNullCuandoNombreDuplicado() {
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(true);

        Huesped resultado = huespedService.guardarHuesped(huesped);

        assertNull(resultado);
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio, never()).save(any());
    }

    /**
     * Verifica que guardarHuesped relanza la excepción si el repositorio falla.
     */
    @Test
    void guardarHuesped_relanzaExcepcionCuandoRepositorioFalla() {
        Huesped huesped = new Huesped(null, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.existsByNombreCompletoIgnoreCase("Benjamin agüero")).thenReturn(false);
        when(huespedRepositorio.save(huesped)).thenThrow(new RuntimeException("Error de BD"));

        assertThrows(RuntimeException.class, () -> huespedService.guardarHuesped(huesped));
        verify(huespedRepositorio).existsByNombreCompletoIgnoreCase("Benjamin agüero");
        verify(huespedRepositorio).save(huesped);
    }

    /**
     * Verifica que actualizarPorId modifica el huésped cuando existe.
     */
    @Test
    void actualizarPorId_actualizaCuandoHuespedExiste() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        Huesped huespedActualizado = new Huesped(id, "Benjamin agüero update", "21659428-2", 22, "turista", "nuevo historial", "nuevo@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenReturn(huespedActualizado);

        Huesped resultado = huespedService.actualizarPorId(id, huespedActualizado);

        assertNotNull(resultado);
        assertEquals("Benjamin agüero update", resultado.getNombreCompleto());
        assertEquals(22, resultado.getEdad());
        assertEquals("turista", resultado.getPerfil());
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarPorId retorna null si el huésped no existe.
     */
    @Test
    void actualizarPorId_retornaNullCuandoNoEncontrado() {
        Long id = 999L;
        Huesped huespedActualizado = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        Huesped resultado = huespedService.actualizarPorId(id, huespedActualizado);

        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).save(any());
    }

    /**
     * Verifica que actualizarPorId relanza la excepción si el repositorio falla.
     */
    @Test
    void actualizarPorId_relanzaExcepcionCuandoRepositorioFalla() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        Huesped huespedActualizado = new Huesped(id, "Benjamin agüero update", "21659428-2", 22, "turista", "nuevo historial", "nuevo@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenThrow(new RuntimeException("Error de BD"));

        assertThrows(RuntimeException.class, () -> huespedService.actualizarPorId(id, huespedActualizado));
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que eliminarHuesped retorna true cuando el huésped existe.
     */
    @Test
    void eliminarHuesped_retornaTrueCuandoExiste() {
        Long id = 1L;
        Huesped huesped = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huesped));
        doNothing().when(huespedRepositorio).deleteById(id);

        boolean resultado = huespedService.eliminarHuesped(id);

        assertTrue(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).deleteById(id);
    }

    /**
     * Verifica que eliminarHuesped retorna false si el huésped no existe.
     */
    @Test
    void eliminarHuesped_retornaFalseCuandoNoEncontrado() {
        Long id = 999L;
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        boolean resultado = huespedService.eliminarHuesped(id);

        assertFalse(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).deleteById(anyLong());
    }

    /**
     * Verifica que eliminarHuesped relanza la excepción si el repositorio falla.
     */
    @Test
    void eliminarHuesped_relanzaExcepcionCuandoRepositorioFalla() {
        Long id = 1L;
        Huesped huesped = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huesped));
        doThrow(new RuntimeException("Error de BD")).when(huespedRepositorio).deleteById(id);

        assertThrows(RuntimeException.class, () -> huespedService.eliminarHuesped(id));
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).deleteById(id);
    }

    /**
     * Verifica que actualizarParcial modifica el nombre completo.
     */
    @Test
    void actualizarParcial_actualizaNombreCompleto() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreCompleto", "Benjamin agüero modificado");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("Benjamin agüero modificado", resultado.getNombreCompleto());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial modifica el RUT.
     */
    @Test
    void actualizarParcial_actualizaRut() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("rut", "11111111-1");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("11111111-1", resultado.getRut());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial modifica la edad.
     */
    @Test
    void actualizarParcial_actualizaEdad() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("edad", 25);

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals(25, resultado.getEdad());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial modifica el perfil.
     */
    @Test
    void actualizarParcial_actualizaPerfil() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("perfil", "turista internacional");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("turista internacional", resultado.getPerfil());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial modifica el historial.
     */
    @Test
    void actualizarParcial_actualizaHistorial() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("historial", "nuevo historial detallado");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("nuevo historial detallado", resultado.getHistorial());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial modifica el correo.
     */
    @Test
    void actualizarParcial_actualizaCorreo() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("correo", "nuevo.correo@gmail.com");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("nuevo.correo@gmail.com", resultado.getCorreo());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial retorna null si el huésped no existe.
     */
    @Test
    void actualizarParcial_retornaNullCuandoNoEncontrado() {
        Long id = 999L;
        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreCompleto", "Nombre Nuevo");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.empty());

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNull(resultado);
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio, never()).save(any());
    }

    /**
     * Verifica que actualizarParcial ignora campos desconocidos sin lanzar error.
     */
    @Test
    void actualizarParcial_campoDesconocido_noLanzaExcepcion() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Map<String, Object> campos = new HashMap<>();
        campos.put("campoInexistente", "valor cualquiera");

        Huesped resultado = huespedService.actualizarParcial(id, campos);

        assertNotNull(resultado);
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        assertEquals("21659428-2", resultado.getRut());
        assertEquals(21, resultado.getEdad());
        verify(huespedRepositorio).save(huespedExistente);
    }

    /**
     * Verifica que actualizarParcial relanza la excepción si el repositorio falla.
     */
    @Test
    void actualizarParcial_relanzaExcepcionCuandoRepositorioFalla() {
        Long id = 1L;
        Huesped huespedExistente = new Huesped(id, "Benjamin agüero", "21659428-2", 21, "chileno", "historial", "benja123@gmail.com");
        when(huespedRepositorio.findById(id)).thenReturn(Optional.of(huespedExistente));
        when(huespedRepositorio.save(any(Huesped.class))).thenThrow(new RuntimeException("Error de BD"));

        Map<String, Object> campos = new HashMap<>();
        campos.put("nombreCompleto", "Nuevo Nombre");

        assertThrows(RuntimeException.class, () -> huespedService.actualizarParcial(id, campos));
        verify(huespedRepositorio).findById(id);
        verify(huespedRepositorio).save(huespedExistente);
    }
}
