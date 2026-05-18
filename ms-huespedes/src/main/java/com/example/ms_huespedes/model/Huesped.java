package com.example.ms_huespedes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;

@Entity
public class Huesped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre obligatorio")
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @NotBlank(message = "Rut obligatorio")
    private String rut;

    @NotNull(message = "Edad obligatoria")
    private Integer edad;

    @NotBlank(message = "Perfil obligatorio")
    private String perfil;

    @NotBlank(message = "Historial obligatorio")
    private String historial;

    @NotBlank(message = "Correo obligatorio")
    private String correo;

    public Huesped() {
    }

    public Huesped(Long id, String nombreCompleto, String rut, Integer edad, String perfil, String historial, String correo) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.rut = rut;
        this.edad = edad;
        this.perfil = perfil;
        this.historial = historial;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
