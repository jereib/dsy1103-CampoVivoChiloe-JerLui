package com.example.ms_actividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Actividad", description = "Representa una actividad en el sistema")
@Entity
@Table(name = "actividades")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadModel {
    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre de la actividad", example = "Taller de pintura")
    private String nombreActividad;

    @Schema(description = "Descripcion de la actividad", example = "Actividad recreativa")
    private String descripcion;

    @Schema(description = "Dia y hora de la actividad", example = "Sábado 15:00")
    private String calendario;

    @Schema(description = "Id de la familia socia", example = "1")
    private Long socioId;
}
