package com.example.ms_fondo.model;

import jakarta.persistence.*;

@Entity
public class DeudaSocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long socioId;
    private double monto;
    private String estado; // ACTIVA o PAGADA

    public DeudaSocio() {
    }

    public DeudaSocio(Long socioId, double monto, String estado) {
        this.socioId = socioId;
        this.monto = monto;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getSocioId() {
        return socioId;
    }

    public void setSocioId(Long socioId) {
        this.socioId = socioId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}