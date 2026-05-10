package com.example.ms_socios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Socio {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre de la familia socia obligatorio")
    private String socio;

    @NotBlank(message = "Nombre del predio obligatorio")
    private String predio;

    @NotNull(message = "Capacidad obligatoria")
    private Integer capacidad;

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
