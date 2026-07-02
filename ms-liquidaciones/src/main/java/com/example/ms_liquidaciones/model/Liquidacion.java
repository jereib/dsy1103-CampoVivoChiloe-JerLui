package com.example.ms_liquidaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

// Entidad que representa una liquidación (ingresos - deuda)
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

    public Liquidacion() {
    }


    public Liquidacion(Long socioId, double ingresos, double deuda, double total) {
        this.socioId = socioId;
        this.ingresos = ingresos;
        this.deuda = deuda;
        this.total = total;
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

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}