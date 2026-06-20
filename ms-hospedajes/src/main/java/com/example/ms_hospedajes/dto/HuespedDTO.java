package com.example.ms_hospedajes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "Huesped DTO", description = "Un DTO que permite obtener ciertos atributos del modelo huesped de ms-huespedes")
@Data
public class HuespedDTO {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @Schema(description = "Nombre del huesped", example = "Benjamin agüero")
    private String nombreCompleto;

    @Schema(description = "Rut del huesped", example = "21659428-2")
    private String rut;

    @Schema(description = "Correo del huesped", example = "benja123@gmail.com")
    private String correo;
}
