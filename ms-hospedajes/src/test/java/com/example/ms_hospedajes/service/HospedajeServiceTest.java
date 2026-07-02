package com.example.ms_hospedajes.service;

import com.example.ms_hospedajes.client.HuespedClient;
import com.example.ms_hospedajes.client.SocioClient;
import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.repository.HospedajeRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio HospedajeService.
 * Verifica la lógica de negocio usando mocks de los clientes Feign y el repositorio.
 */
@ExtendWith(MockitoExtension.class)
class HospedajeServiceTest {

    @Mock
    private HuespedClient huespedClient;

    @Mock
    private SocioClient socioClient;

    @Mock
    private HospedajeRepositorio hospedajeRepositorio;

    @InjectMocks
    private HospedajeService hospedajeService;

    @Captor
    private ArgumentCaptor<HospedajeModel> hospedajeCaptor;

    /**
     * Debe retornar todos los hospedajes registrados.
     */
    @Test
    void listar_retornaTodosLosHospedajes() {
        List<HospedajeModel> hospedajes = List.of(
                crearHospedaje(1L, 1L, 1L),
                crearHospedaje(2L, 2L, 2L)
        );
        when(hospedajeRepositorio.findAll()).thenReturn(hospedajes);

        List<HospedajeModel> resultado = hospedajeService.listar();

        assertEquals(2, resultado.size());
        verify(hospedajeRepositorio).findAll();
    }

    /**
     * Debe retornar un DTO de huésped cuando el cliente Feign responde correctamente.
     */
    @Test
    void obtenerHuesped_retornaHuespedDTO_cuandoExiste() {
        Long id = 1L;
        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(id);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        when(huespedClient.obtenerHuesped(id)).thenReturn(huespedDTO);

        HuespedDTO resultado = hospedajeService.obtenerHuesped(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedClient).obtenerHuesped(id);
    }

    /**
     * Debe retornar null cuando el cliente Feign devuelve null.
     */
    @Test
    void obtenerHuesped_retornaNull_cuandoFeignRetornaNull() {
        Long id = 1L;
        when(huespedClient.obtenerHuesped(id)).thenReturn(null);

        HuespedDTO resultado = hospedajeService.obtenerHuesped(id);

        assertNull(resultado);
        verify(huespedClient).obtenerHuesped(id);
    }

    /**
     * Debe lanzar excepción cuando el cliente Feign falla en la comunicación.
     */
    @Test
    void obtenerHuesped_lanzaExcepcion_cuandoFeignFalla() {
        Long id = 1L;
        when(huespedClient.obtenerHuesped(id)).thenThrow(new RuntimeException("Error de comunicación"));

        assertThrows(RuntimeException.class, () -> hospedajeService.obtenerHuesped(id));
        verify(huespedClient).obtenerHuesped(id);
    }

    /**
     * Debe retornar un DTO de socio cuando el cliente Feign responde correctamente.
     */
    @Test
    void obtenerSocio_retornaSocioDTO_cuandoExiste() {
        Long id = 1L;
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(id);
        socioDTO.setSocio("Los Jackson");

        when(socioClient.obtenerSocio(id)).thenReturn(socioDTO);

        SocioDTO resultado = hospedajeService.obtenerSocio(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Los Jackson", resultado.getSocio());
        verify(socioClient).obtenerSocio(id);
    }

    /**
     * Debe retornar null cuando el cliente Feign devuelve null.
     */
    @Test
    void obtenerSocio_retornaNull_cuandoFeignRetornaNull() {
        Long id = 1L;
        when(socioClient.obtenerSocio(id)).thenReturn(null);

        SocioDTO resultado = hospedajeService.obtenerSocio(id);

        assertNull(resultado);
        verify(socioClient).obtenerSocio(id);
    }

    /**
     * Debe lanzar excepción cuando el cliente Feign falla en la comunicación.
     */
    @Test
    void obtenerSocio_lanzaExcepcion_cuandoFeignFalla() {
        Long id = 1L;
        when(socioClient.obtenerSocio(id)).thenThrow(new RuntimeException("Error de comunicación"));

        assertThrows(RuntimeException.class, () -> hospedajeService.obtenerSocio(id));
        verify(socioClient).obtenerSocio(id);
    }

    /**
     * Debe crear un hospedaje cuando ambos clientes Feign responden correctamente.
     */
    @Test
    void crearHospedaje_creaHospedaje_cuandoAmbosFeignExitosos() {
        Long socioId = 1L;
        Long huespedId = 2L;

        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(huespedId);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(socioId);
        socioDTO.setSocio("Los Jackson");

        HospedajeModel hospedajeGuardado = new HospedajeModel();
        hospedajeGuardado.setId(1L);
        hospedajeGuardado.setHuespedId(huespedId);
        hospedajeGuardado.setSocioId(socioId);

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(huespedDTO);
        when(socioClient.obtenerSocio(socioId)).thenReturn(socioDTO);
        when(hospedajeRepositorio.save(any(HospedajeModel.class))).thenReturn(hospedajeGuardado);

        String resultado = hospedajeService.crearHospedaje(socioId, huespedId);

        assertNotNull(resultado);
        assertTrue(resultado.contains("Benjamin agüero"));
        assertTrue(resultado.contains("Los Jackson"));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio).save(any(HospedajeModel.class));
    }

    /**
     * Debe lanzar excepción cuando el huésped no se encuentra.
     */
    @Test
    void crearHospedaje_lanzaExcepcion_cuandoHuespedNoEncontrado() {
        Long socioId = 1L;
        Long huespedId = 999L;

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient, never()).obtenerSocio(anyLong());
        verify(hospedajeRepositorio, never()).save(any());
    }

    /**
     * Debe lanzar excepción cuando el socio no se encuentra.
     */
    @Test
    void crearHospedaje_lanzaExcepcion_cuandoSocioNoEncontrado() {
        Long socioId = 999L;
        Long huespedId = 2L;

        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(huespedId);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(huespedDTO);
        when(socioClient.obtenerSocio(socioId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio, never()).save(any());
    }

    /**
     * Debe lanzar excepción cuando el cliente Feign falla al validar el huésped.
     */
    @Test
    void crearHospedaje_lanzaExcepcion_cuandoFeignFalla() {
        Long socioId = 1L;
        Long huespedId = 2L;

        when(huespedClient.obtenerHuesped(huespedId)).thenThrow(new RuntimeException("Error de comunicación"));

        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient, never()).obtenerSocio(anyLong());
        verify(hospedajeRepositorio, never()).save(any());
    }

    /**
     * Debe lanzar excepción cuando el repositorio falla al guardar.
     */
    @Test
    void crearHospedaje_lanzaExcepcion_cuandoRepositorioFalla() {
        Long socioId = 1L;
        Long huespedId = 2L;

        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(huespedId);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(socioId);
        socioDTO.setSocio("Los Jackson");

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(huespedDTO);
        when(socioClient.obtenerSocio(socioId)).thenReturn(socioDTO);
        when(hospedajeRepositorio.save(any(HospedajeModel.class))).thenThrow(new RuntimeException("Error de BD"));

        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio).save(any(HospedajeModel.class));
    }

    /**
     * Debe actualizar un hospedaje cuando existe.
     */
    @Test
    void actualizar_actualizaHospedaje_cuandoExiste() {
        Long id = 1L;
        HospedajeModel existente = crearHospedaje(id, 1L, 1L);

        HospedajeModel datos = new HospedajeModel();
        datos.setSocioId(2L);
        datos.setHuespedId(2L);

        HospedajeModel actualizado = crearHospedaje(id, 2L, 2L);

        when(hospedajeRepositorio.findById(id)).thenReturn(Optional.of(existente));
        when(hospedajeRepositorio.save(any(HospedajeModel.class))).thenReturn(actualizado);

        HospedajeModel resultado = hospedajeService.actualizar(id, datos);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getSocioId());
        assertEquals(2L, resultado.getHuespedId());
        verify(hospedajeRepositorio).findById(id);
        verify(hospedajeRepositorio).save(existente);
    }

    /**
     * Debe retornar null cuando el hospedaje a actualizar no existe.
     */
    @Test
    void actualizar_retornaNull_cuandoNoExiste() {
        Long id = 999L;
        HospedajeModel datos = new HospedajeModel();
        datos.setSocioId(2L);
        datos.setHuespedId(2L);

        when(hospedajeRepositorio.findById(id)).thenReturn(Optional.empty());

        HospedajeModel resultado = hospedajeService.actualizar(id, datos);

        assertNull(resultado);
        verify(hospedajeRepositorio).findById(id);
        verify(hospedajeRepositorio, never()).save(any());
    }

    /**
     * Debe retornar true cuando el hospedaje existe y se elimina.
     */
    @Test
    void eliminar_retornaTrue_cuandoExiste() {
        Long id = 1L;
        when(hospedajeRepositorio.existsById(id)).thenReturn(true);
        doNothing().when(hospedajeRepositorio).deleteById(id);

        boolean resultado = hospedajeService.eliminar(id);

        assertTrue(resultado);
        verify(hospedajeRepositorio).existsById(id);
        verify(hospedajeRepositorio).deleteById(id);
    }

    /**
     * Debe retornar false cuando el hospedaje a eliminar no existe.
     */
    @Test
    void eliminar_retornaFalse_cuandoNoExiste() {
        Long id = 999L;
        when(hospedajeRepositorio.existsById(id)).thenReturn(false);

        boolean resultado = hospedajeService.eliminar(id);

        assertFalse(resultado);
        verify(hospedajeRepositorio).existsById(id);
        verify(hospedajeRepositorio, never()).deleteById(anyLong());
    }

    /**
     * Crea un objeto HospedajeModel con los valores dados.
     *
     * @param id        identificador del hospedaje.
     * @param socioId   identificador del socio.
     * @param huespedId identificador del huésped.
     * @return hospedaje configurado.
     */
    private HospedajeModel crearHospedaje(Long id, Long socioId, Long huespedId) {
        HospedajeModel h = new HospedajeModel();
        h.setId(id);
        h.setSocioId(socioId);
        h.setHuespedId(huespedId);
        return h;
    }
}
