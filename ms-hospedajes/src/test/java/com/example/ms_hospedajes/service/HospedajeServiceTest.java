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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Test
    void obtenerHuesped_retornaHuespedDTO_cuandoExiste() {
        // given
        Long id = 1L;
        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(id);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        when(huespedClient.obtenerHuesped(id)).thenReturn(huespedDTO);

        // when
        HuespedDTO resultado = hospedajeService.obtenerHuesped(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Benjamin agüero", resultado.getNombreCompleto());
        verify(huespedClient).obtenerHuesped(id);
    }

    @Test
    void obtenerHuesped_lanzaExcepcion_cuandoFeignFalla() {
        // given
        Long id = 1L;
        when(huespedClient.obtenerHuesped(id)).thenThrow(new RuntimeException("Error de comunicación"));

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.obtenerHuesped(id));
        verify(huespedClient).obtenerHuesped(id);
    }

    @Test
    void obtenerSocio_retornaSocioDTO_cuandoExiste() {
        // given
        Long id = 1L;
        SocioDTO socioDTO = new SocioDTO();
        socioDTO.setId(id);
        socioDTO.setSocio("Los Jackson");

        when(socioClient.obtenerSocio(id)).thenReturn(socioDTO);

        // when
        SocioDTO resultado = hospedajeService.obtenerSocio(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Los Jackson", resultado.getSocio());
        verify(socioClient).obtenerSocio(id);
    }

    @Test
    void obtenerSocio_lanzaExcepcion_cuandoFeignFalla() {
        // given
        Long id = 1L;
        when(socioClient.obtenerSocio(id)).thenThrow(new RuntimeException("Error de comunicación"));

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.obtenerSocio(id));
        verify(socioClient).obtenerSocio(id);
    }

    @Test
    void crearHospedaje_creaHospedaje_cuandoAmbosFeignExitosos() {
        // given
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

        // when
        String resultado = hospedajeService.crearHospedaje(socioId, huespedId);

        // then
        assertNotNull(resultado);
        assertTrue(resultado.contains("Benjamin agüero"));
        assertTrue(resultado.contains("Los Jackson"));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio).save(any(HospedajeModel.class));
    }

    @Test
    void crearHospedaje_lanzaExcepcion_cuandoHuespedNoEncontrado() {
        // given
        Long socioId = 1L;
        Long huespedId = 999L;

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient, never()).obtenerSocio(anyLong());
        verify(hospedajeRepositorio, never()).save(any());
    }

    @Test
    void crearHospedaje_lanzaExcepcion_cuandoSocioNoEncontrado() {
        // given
        Long socioId = 999L;
        Long huespedId = 2L;

        HuespedDTO huespedDTO = new HuespedDTO();
        huespedDTO.setId(huespedId);
        huespedDTO.setNombreCompleto("Benjamin agüero");

        when(huespedClient.obtenerHuesped(huespedId)).thenReturn(huespedDTO);
        when(socioClient.obtenerSocio(socioId)).thenReturn(null);

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio, never()).save(any());
    }

    @Test
    void crearHospedaje_lanzaExcepcion_cuandoFeignFalla() {
        // given
        Long socioId = 1L;
        Long huespedId = 2L;

        when(huespedClient.obtenerHuesped(huespedId)).thenThrow(new RuntimeException("Error de comunicación"));

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient, never()).obtenerSocio(anyLong());
        verify(hospedajeRepositorio, never()).save(any());
    }

    @Test
    void crearHospedaje_lanzaExcepcion_cuandoRepositorioFalla() {
        // given
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

        // when & then
        assertThrows(RuntimeException.class, () -> hospedajeService.crearHospedaje(socioId, huespedId));
        verify(huespedClient).obtenerHuesped(huespedId);
        verify(socioClient).obtenerSocio(socioId);
        verify(hospedajeRepositorio).save(any(HospedajeModel.class));
    }
}
