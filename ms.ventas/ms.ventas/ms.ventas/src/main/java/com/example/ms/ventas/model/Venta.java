package com.example.ms.ventas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Schema(name = "Venta", description = "Representa una venta registrada en el sistema")
@Entity
public class Venta {
    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Id del producto", example = "1")
    private Long productoId;

    @Schema(description = "Cantidad de productos en la venta", example = "5")
    private Integer cantidad;

    @Schema(description = "Valor total de la venta", example = "15000")
    private Double total;

    @Schema(description = "Medio o plataforma donde se realizo la venta", example = "WEB")
    private String canal;

    @Schema(description = "Fecha de la venta", example = "2026-06-20T15:48:00")
    private LocalDateTime fechaVenta;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
}