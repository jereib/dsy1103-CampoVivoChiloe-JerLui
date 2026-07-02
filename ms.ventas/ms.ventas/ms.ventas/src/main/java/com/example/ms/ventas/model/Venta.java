package com.example.ms.ventas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una venta registrada en el sistema.
 * Cada venta está asociada a un producto y contiene cantidad,
 * total, canal y fecha en que se realizó.
 */
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

    /**
     * Devuelve el identificador único de la venta.
     */
    public Long getId() { return id; }
    /**
     * Asigna el identificador único de la venta.
     */
    public void setId(Long id) { this.id = id; }
    /**
     * Devuelve el id del producto asociado a la venta.
     */
    public Long getProductoId() { return productoId; }
    /**
     * Asigna el id del producto asociado a la venta.
     */
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    /**
     * Devuelve la cantidad de productos vendidos.
     */
    public Integer getCantidad() { return cantidad; }
    /**
     * Asigna la cantidad de productos vendidos.
     */
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    /**
     * Devuelve el valor total de la venta.
     */
    public Double getTotal() { return total; }
    /**
     * Asigna el valor total de la venta.
     */
    public void setTotal(Double total) { this.total = total; }
    /**
     * Devuelve el canal por el que se realizó la venta.
     */
    public String getCanal() { return canal; }
    /**
     * Asigna el canal por el que se realizó la venta.
     */
    public void setCanal(String canal) { this.canal = canal; }
    /**
     * Devuelve la fecha en que se realizó la venta.
     */
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    /**
     * Asigna la fecha en que se realizó la venta.
     */
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }
}