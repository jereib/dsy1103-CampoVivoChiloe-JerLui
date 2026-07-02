package com.example.ms_socios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Familia socia", description = "Representa una familia socia del sistema")
@Entity
/**
 * Modelo principal que representa una familia socia.
 * Se mapea a la tabla de socios en la base de datos.
 */
public class Socio {

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

    /**
     * Constructor vacío requerido por JPA.
     */
    public Socio() {
    }

    /**
     * Constructor con todos los atributos de la familia socia.
     *
     * @param id        identificador único
     * @param socio     nombre de la familia socia
     * @param predio    nombre del predio
     * @param capacidad capacidad de la familia
     * @param estado    estado actual (DISPONIBLE, SUSPENDIDO, MANTENIMIENTO)
     */
    public Socio(Long id, String socio, String predio, Integer capacidad, Estado estado) {
        this.id = id;
        this.socio = socio;
        this.predio = predio;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    /**
     * @return el identificador único del socio
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id identificador único a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return el nombre de la familia socia
     */
    public String getSocio() {
        return socio;
    }

    /**
     * @param socio nombre de la familia socia a asignar
     */
    public void setSocio(String socio) {
        this.socio = socio;
    }

    /**
     * @return el nombre del predio
     */
    public String getPredio() {
        return predio;
    }

    /**
     * @param predio nombre del predio a asignar
     */
    public void setPredio(String predio) {
        this.predio = predio;
    }

    /**
     * @return la capacidad de la familia socia
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad capacidad a asignar
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return el estado actual del socio
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * @param estado estado a asignar (DISPONIBLE, SUSPENDIDO, MANTENIMIENTO)
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
