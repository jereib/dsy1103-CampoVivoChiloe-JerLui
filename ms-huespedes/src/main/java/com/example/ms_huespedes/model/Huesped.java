package com.example.ms_huespedes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;

@Schema(name = "Huesped", description = "Representa a un huesped el sistema")
@Entity
public class Huesped {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del huesped", example = "Benjamin agüero")
    @NotBlank(message = "Nombre obligatorio")
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Schema(description = "Rut del huesped", example = "21659428-2")
    @NotBlank(message = "Rut obligatorio")
    private String rut;

    @Schema(description = "Edad del huesped", example = "21")
    @NotNull(message = "Edad obligatoria")
    private Integer edad;

    @Schema(description = "Perfil sobre el huesped", example = "chileno que le gusta comer completos")
    @NotBlank(message = "Perfil obligatorio")
    private String perfil;

    @Schema(description = "Historial sobre el huesped", example = "Se hospedó anteriormente en 2024 y solicitó servicio de desayuno incluido")
    @NotBlank(message = "Historial obligatorio")
    private String historial;

    @Schema(description = "Correo del huesped", example = "benja123@gmail.com")
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
