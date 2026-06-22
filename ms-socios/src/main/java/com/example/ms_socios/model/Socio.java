package com.example.ms_socios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Familia socia", description = "Representa una familia socia del sistema")
@Entity
public class Socio {
    //atributos
    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre de la familia socia", example = "Los Krausse")
    @NotBlank(message = "Nombre de la familia socia obligatorio")
    private String socio;

    @Schema(description = "Nombre del predio", example = "Los abetos")
    @NotBlank(message = "Nombre del predio obligatorio")
    private String predio;

    @Schema(description = "Capacidad de la familia socia", example = "15")
    @NotNull(message = "Capacidad obligatoria")
    private Integer capacidad;

    @Schema(description = "Estado de la familia socia", example = "SUSPENDIDO")
    @NotNull(message = "Estado obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado;

    //constructor vacio
    public Socio() {
    }

    //constructor con los atributos
    public Socio(Long id, String socio, String predio, Integer capacidad, Estado estado) {
        this.id = id;
        this.socio = socio;
        this.predio = predio;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    //getter and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocio() {
        return socio;
    }

    public void setSocio(String socio) {
        this.socio = socio;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
