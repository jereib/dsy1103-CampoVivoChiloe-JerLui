package com.example.ms_hospedajes.service;

import com.example.ms_hospedajes.client.HuespedClient;
import com.example.ms_hospedajes.client.SocioClient;
import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.repository.HospedajeRepositorio;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class HospedajeService {
    private static final Logger logger = LoggerFactory.getLogger(HospedajeService.class);
    private final HuespedClient huespedClient;
    private final SocioClient socioClient;
    private final HospedajeRepositorio hospedajeRepositorio;

    public HospedajeService(HuespedClient huespedClient,
                            SocioClient socioClient,
                            HospedajeRepositorio hospedajeRepositorio) {
        this.huespedClient = huespedClient;
        this.socioClient = socioClient;
        this.hospedajeRepositorio = hospedajeRepositorio;
    }

    public HuespedDTO obtenerHuesped(Long id){
        return huespedClient.obtenerHuesped(id);
    }

    public SocioDTO obtenerSocio(Long id){
        return socioClient.obtenerSocio(id);
    }

    public String crearHospedaje(Long socioId, Long huespedId){

        HuespedDTO huesped = huespedClient.obtenerHuesped(huespedId);
        SocioDTO socio = socioClient.obtenerSocio(socioId);

        HospedajeModel hospedaje = new HospedajeModel();
        hospedaje.setSocioId(socioId);
        hospedaje.setHuespedId(huespedId);

        hospedajeRepositorio.save(hospedaje);

        logger.info("Hospedaje creado correctamente");

        return "Hospedaje creado para el huésped: "
                + huesped.getNombreCompleto()
                + " | Familia socia: "
                + socio.getSocio();
    }
}
