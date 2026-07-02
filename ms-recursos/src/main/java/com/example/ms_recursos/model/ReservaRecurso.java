package com.example.ms_recursos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad JPA que representa la reserva de un recurso por parte de un socio.
 * Almacena informacion del recurso, fechas de uso y estado de la reserva.
 */
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

    /**
     * Constructor vacio requerido por JPA.
     */
    public ReservaRecurso() {
    }

    /**
     * Constructor con todos los campos excepto el id.
     *
     * @param socioId       id del socio que reserva
     * @param nombreRecurso nombre del recurso
     * @param fechaInicio   fecha de inicio de la reserva
     * @param fechaFin      fecha de termino de la reserva
     * @param estado        estado de la reserva
     */
    public ReservaRecurso(Long socioId, String nombreRecurso, String fechaInicio, String fechaFin, String estado) {
        this.socioId = socioId;
        this.nombreRecurso = nombreRecurso;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    /**
     * Retorna el id de la reserva.
     *
     * @return id de la reserva
     */
    public Long getId() {
        return id;
    }

    /**
     * Asigna el id de la reserva.
     *
     * @param id identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retorna el id del socio que hizo la reserva.
     *
     * @return id del socio
     */
    public Long getSocioId() {
        return socioId;
    }

    /**
     * Asigna el id del socio.
     *
     * @param socioId identificador del socio
     */
    public void setSocioId(Long socioId) {
        this.socioId = socioId;
    }

    /**
     * Retorna el nombre del recurso reservado.
     *
     * @return nombre del recurso
     */
    public String getNombreRecurso() {
        return nombreRecurso;
    }

    /**
     * Asigna el nombre del recurso.
     *
     * @param nombreRecurso nombre del recurso
     */
    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    /**
     * Retorna la fecha de inicio de la reserva.
     *
     * @return fecha de inicio
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Asigna la fecha de inicio.
     *
     * @param fechaInicio fecha de inicio
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Retorna la fecha de termino de la reserva.
     *
     * @return fecha de termino
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * Asigna la fecha de termino.
     *
     * @param fechaFin fecha de termino
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Retorna el estado actual de la reserva.
     *
     * @return estado de la reserva
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asigna el estado de la reserva.
     *
     * @param estado estado de la reserva
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}