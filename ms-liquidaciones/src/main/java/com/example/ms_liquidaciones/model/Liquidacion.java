package com.example.ms_liquidaciones.model;

public class Liquidacion {

    private Long socioId;
    private double ingresos;
    private double deuda;
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