package com.example.ms_socios.service;

import com.example.ms_socios.model.Estado;
import com.example.ms_socios.model.Socio;
import com.example.ms_socios.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {
    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository){
        this.socioRepository = socioRepository;
    }

    //muestra las familias socias registradas
    public List<Socio> listarSocios(){
        return socioRepository.findAll();
    }

    //busca por el estado de la familia socia
    public List<Socio> buscarPorEstado(Estado estado){
        List<Socio> socio = socioRepository.findByEstado(estado);

        return socio;
    }

    //busca por el id de la familia socia
    public Socio buscarPorId(Long id){
        Optional<Socio> socio =
                socioRepository.findById(id);

        return socio.orElse(null);
    }

    //guarda la familia socia
    public Socio guardarSocio(Socio socio){
        boolean existe =
                socioRepository.existsBySocioIgnoreCase(
                        socio.getSocio()
                );

        if(existe){
            return null;
        }

        return socioRepository.save(socio);
    }

    //actualiza por id la familia socia
    public Socio actualizarPorId(Long id, Socio socioActualizado){
        Optional<Socio> socioExistente =
                socioRepository.findById(id);

        if(socioExistente.isEmpty()){
            return null;
        }

        Socio socio = socioExistente.get();

        socio.setSocio(socioActualizado.getSocio());
        socio.setPredio(socioActualizado.getPredio());
        socio.setCapacidad(socioActualizado.getCapacidad());
        socio.setEstado(socioActualizado.getEstado());

        return socioRepository.save(socio);
    }


    //elimina a la familia socia por id
    public boolean eliminarSocio(Long id){

        Optional<Socio> socio =
                socioRepository.findById(id);

        if(socio.isEmpty()){
            return false;
        }

        socioRepository.deleteById(id);

        return true;
    }
}
