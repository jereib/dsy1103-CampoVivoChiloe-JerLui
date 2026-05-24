package com.example.ms_actividades.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadesResponseDTO {
    private String nombreActividad;

    private String descripcion;

    private String calendario;

    private String socio;
}
