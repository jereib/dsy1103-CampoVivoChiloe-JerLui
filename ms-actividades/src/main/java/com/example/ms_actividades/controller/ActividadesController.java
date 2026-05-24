package com.example.ms_actividades.controller;

import com.example.ms_actividades.dto.ActividadesResponseDTO;
import com.example.ms_actividades.model.ActividadModel;
import com.example.ms_actividades.service.ActividadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/actividades")
public class ActividadesController {

    @Autowired
    private ActividadesService actividadService;

    @GetMapping
    public ResponseEntity<List<ActividadesResponseDTO>> listarActividades(){

        return ResponseEntity.ok(
                actividadService.listarActividades()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerActividad(@PathVariable Long id){

        try {

            ActividadesResponseDTO actividad =
                    actividadService.obtenerActividad(id);

            return ResponseEntity.ok(actividad);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearActividad(@RequestBody ActividadModel actividad){

        ActividadModel nuevaActividad = actividadService.crearActividad(actividad);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevaActividad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarActividad(
            @PathVariable Long id,
            @RequestBody ActividadModel actividadActualizada){

        try {

            ActividadModel actividad = actividadService
                    .actualizarActividad(id, actividadActualizada);

            return ResponseEntity.ok(actividad);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarActividad(@PathVariable Long id){

        try {

            actividadService.eliminarActividad(id);

            return ResponseEntity.ok("Actividad eliminada correctamente");

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
