package com.example.ms_actividades.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Socio DTO", description = "Un DTO que permite obtener ciertos atributos del modelo socio de ms-socios, en este caso solo el nombre de la familia socia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocioDTO {
    @Schema(description = "Id de la familia socia", example = "1")
    private Long id;

    @Schema(description = "Nombre de la familia socia", example = "Los Jackson")
    private String socio;
}
