package com.example.ms_hospedajes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(name = "Hospedaje", description = "Representa un hospedaje en el sistema")
@Data
@Entity
/**
 * Entidad que representa la relación entre un socio y un huésped.
 * Funciona como tabla intermedia en la base de datos.
 */
public class HospedajeModel {
    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Id de un huesped", example = "2")
    private Long huespedId;

    @Schema(description = "Id de una familia socia", example = "3")
    private Long socioId;
}
