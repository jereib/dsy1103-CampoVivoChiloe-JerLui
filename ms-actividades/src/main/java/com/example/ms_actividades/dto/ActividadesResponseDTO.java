package com.example.ms_actividades.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Actividad DTO", description = "Representa una actividad en el sistema, pero cambiando el id de la familia socia por el nombre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadesResponseDTO {

    @Schema(description = "Nombre de la actividad", example = "Taller de pintura")
    private String nombreActividad;

    @Schema(description = "Descripcion de la actividad", example = "Actividad recreativa")
    private String descripcion;

    @Schema(description = "Dia y hora de la actividad", example = "Sábado 15:00")
    private String calendario;

    @Schema(description = "Nombre de la familia socia", example = "Los Jackson")
    private String socio;
}
