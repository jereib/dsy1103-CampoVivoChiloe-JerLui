package com.example.ms_huespedes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;

/**
 * Entidad que representa a un huésped en el sistema.
 * Almacena información personal como nombre, RUT, edad, perfil, historial y correo.
 */
@Schema(name = "Huesped", description = "Representa a un huesped el sistema")
@Entity
public class Huesped {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del huesped", example = "Benjamin agüero")
    @NotBlank(message = "Nombre obligatorio")
    @Column(name = "nombre_completo") // Mapea a la columna con snake_case en la BD
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

    /**
     * Constructor vacío requerido por JPA.
     */
    public Huesped() {
    }

    /**
     * Constructor con todos los campos del huésped.
     *
     * @param id             identificador del huésped.
     * @param nombreCompleto nombre completo del huésped.
     * @param rut            RUT del huésped.
     * @param edad           edad del huésped.
     * @param perfil         perfil o descripción del huésped.
     * @param historial      historial de estadías del huésped.
     * @param correo         correo electrónico del huésped.
     */
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
