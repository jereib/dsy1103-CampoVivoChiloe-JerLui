package com.example.ms_huespedes.service;

import com.example.ms_huespedes.model.Huesped;
import com.example.ms_huespedes.repository.HuespedRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HuespedService {
    private final HuespedRepositorio huespedRepositorio;

    public HuespedService(HuespedRepositorio huespedRepositorio) {
        this.huespedRepositorio = huespedRepositorio;
    }

    public List<Huesped> listarHuespedes(){
        return huespedRepositorio.findAll();
    }

    public Huesped buscarPorId(Long id){
        Optional<Huesped> huesped =
                huespedRepositorio.findById(id);

        return huesped.orElse(null);
    }

    public Huesped guardarHuesped(Huesped huesped){
        boolean existe =
                huespedRepositorio.existsByNombreCompletoIgnoreCase(
                        huesped.getNombreCompleto()
                );

        if(existe) {
            return null;
        }
        return huespedRepositorio.save(huesped);
    }

    public Huesped actualizarPorId(Long id, Huesped huespedActualizado){
        Optional<Huesped> huespedExistente =
                huespedRepositorio.findById(id);

        if(huespedExistente.isEmpty()){
            return null;
        }

        Huesped huesped = huespedExistente.get();

        huesped.setNombreCompleto(huespedActualizado.getNombreCompleto());
        huesped.setRut(huespedActualizado.getRut());
        huesped.setEdad(huespedActualizado.getEdad());
        huesped.setPerfil(huespedActualizado.getPerfil());
        huesped.setHistorial(huespedActualizado.getHistorial());
        huesped.setCorreo(huespedActualizado.getCorreo());

        return huespedRepositorio.save(huesped);
    }


    public boolean eliminarHuesped(Long id){
        Optional<Huesped> huesped =
                huespedRepositorio.findById(id);

        if(huesped.isEmpty()){
            return false;
        }

        huespedRepositorio.deleteById(id);

        return true;
    }
}
