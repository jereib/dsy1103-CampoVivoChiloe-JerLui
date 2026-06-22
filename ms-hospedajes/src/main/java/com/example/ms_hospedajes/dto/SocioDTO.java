package com.example.ms_hospedajes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Socio DTO", description = "Un DTO que permite obtener ciertos atributos del modelo socio de ms-socios")
@Data
public class SocioDTO {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @Schema(description = "Nombre de la familia socia", example = "Los Jackson")
    private String socio;

    @Schema(description = "Predio de la familia socia", example = "Los avellanos")
    private String predio;

    @Schema(description = "Capacidad de la familia socia", example = "15")
    private Integer capacidad;
}
