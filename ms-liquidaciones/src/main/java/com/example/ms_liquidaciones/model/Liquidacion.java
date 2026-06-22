package com.example.ms_liquidaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Liquidacion", description = "Representa una liquidacion del sistema")
public class Liquidacion {

    @Schema(description = "Id socio", example = "1")
    private Long socioId;

    @Schema(description = "Cantidad de ingresos", example = "300000")
    private double ingresos;

    @Schema(description = "Deuda", example = "50000")
    private double deuda;

    @Schema(description = "Total", example = "250000")
    private double total;

    public Liquidacion() {
    }


    public Liquidacion(Long socioId, double ingresos, double deuda, double total) {
        this.socioId = socioId;
        this.ingresos = ingresos;
        this.deuda = deuda;
        this.total = total;
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