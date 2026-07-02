package com.example.ms_recursos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Entidad que representa la reserva de un recurso por parte de un socio
@Schema(name = "Recurso", description = "Representa un recurso del sistema")
@Entity
public class ReservaRecurso {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El socioId es obligatorio")
    @Schema(description = "Id de la familia socia", example = "1")
    private Long socioId;

    @NotBlank(message = "El nombre del recurso es obligatorio")
    @Schema(description = "Nombre del recurso", example = "Tractor agricola")
    private String nombreRecurso;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    @Schema(description = "Fecha de inicio del recurso", example = "2026-06-10")
    private String fechaInicio;

    @NotBlank(message = "La fecha fin es obligatoria")
    @Schema(description = "Fecha fin del recurso", example = "2026-06-12")
    private String fechaFin;

    @NotBlank(message = "El estado es obligatorio")
    @Schema(description = "Estado del recurso", example = "RESERVADO")
    private String estado;

    // Constructor vacío necesario para JPA
    public ReservaRecurso() {
    }

    public ReservaRecurso(Long socioId, String nombreRecurso, String fechaInicio, String fechaFin, String estado) {
        this.socioId = socioId;
        this.nombreRecurso = nombreRecurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSocioId() {
        return socioId;
    }

    public void setSocioId(Long socioId) {
        this.socioId = socioId;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}