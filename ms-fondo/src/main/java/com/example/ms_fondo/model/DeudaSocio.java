package com.example.ms_fondo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(name = "Deudas", description = "Representa una deuda en el sistema")
@Entity
public class DeudaSocio {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Id de la familia socia", example = "1")
    private Long socioId;

    @Schema(description = "Monto de la deuda", example = "30000")
    private double monto;

    @Schema(description = "Estado de la deuda", example = "PAGADA")
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