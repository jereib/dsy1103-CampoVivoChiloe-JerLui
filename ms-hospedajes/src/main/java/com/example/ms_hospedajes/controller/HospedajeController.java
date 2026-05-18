package com.example.ms_hospedajes.controller;

import com.example.ms_hospedajes.dto.HuespedDTO;
import com.example.ms_hospedajes.dto.SocioDTO;
import com.example.ms_hospedajes.model.HospedajeModel;
import com.example.ms_hospedajes.service.HospedajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospedajes")
public class HospedajeController {
    private final HospedajeService hospedajeService;

    public HospedajeController(HospedajeService hospedajeService){
        this.hospedajeService = hospedajeService;
    }

    @GetMapping("/huespedes/{id}")
    public HuespedDTO obtenerHuesped(@PathVariable Long id){
        return hospedajeService.obtenerHuesped(id);
    }

    @GetMapping("/socios/{id}")
    public SocioDTO obtenerSocio(@PathVariable Long id){
        return hospedajeService.obtenerSocio(id);
    }

    @GetMapping("/crear/{socioId}/{huespedId}")
    public ResponseEntity<?> crearHospedaje(
            @PathVariable Long socioId,
            @PathVariable Long huespedId){

        try{

            String mensaje =
                    hospedajeService.crearHospedaje(socioId, huespedId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mensaje);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: huesped o socio no encontrado");
        }
    }
}
