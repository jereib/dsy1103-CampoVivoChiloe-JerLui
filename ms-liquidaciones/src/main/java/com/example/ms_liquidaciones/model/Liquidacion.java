package com.example.ms_liquidaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa una liquidación.
 * Contiene los ingresos, la deuda y el total calculado para un socio.
 */
@Schema(name = "Liquidacion", description = "Representa una liquidacion del sistema")
@Entity
public class Liquidacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El socioId es obligatorio")
    @Schema(description = "Id socio", example = "1")
    private Long socioId;

    @Schema(description = "Cantidad de ingresos", example = "300000")
    private double ingresos;

    @Schema(description = "Deuda", example = "50000")
    private double deuda; // monto que se descuenta si el socio debe

    @Schema(description = "Total", example = "250000")
    private double total; // ingresos - deuda

    /**
     * Constructor vacío requerido por JPA.
     */
    public Liquidacion() {
    }


    /**
     * Constructor con todos los campos excepto el id.
     *
     * @param socioId identificador del socio.
     * @param ingresos monto de ingresos.
     * @param deuda    monto de deuda.
     * @param total    monto total (ingresos - deuda).
     */
    public Liquidacion(Long socioId, double ingresos, double deuda, double total) {
        this.socioId = socioId;
        this.ingresos = ingresos;
        this.deuda = deuda;
        this.total = total;
    }



    /**
     * @return id de la liquidación.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id nuevo id de la liquidación.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return identificador del socio asociado.
     */
    public Long getSocioId() {
        return socioId;
    }

    /**
     * @param socioId nuevo identificador del socio.
     */
    public void setSocioId(Long socioId) {
        this.socioId = socioId;
    }

    /**
     * @return monto de ingresos del socio.
     */
    public double getIngresos() {
        return ingresos;
    }

    /**
     * @param ingresos nuevo monto de ingresos.
     */
    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    /**
     * @return monto de deuda del socio.
     */
    public double getDeuda() {
        return deuda;
    }

    /**
     * @param deuda nuevo monto de deuda.
     */
    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    /**
     * @return total calculado (ingresos - deuda).
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total nuevo total.
     */
    public void setTotal(double total) {
        this.total = total;
    }
}