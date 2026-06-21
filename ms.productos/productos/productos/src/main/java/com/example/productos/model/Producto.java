package com.example.productos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Schema(name = "Producto", description = "Representa un producto en el sistema")
@Entity
public class Producto {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del producto", example = "Pan")
    private String nombre;

    @Schema(description = "Precio del producto", example = "1200")
    private Double precio;

    @Schema(description = "Cantidad del producto", example = "15")
    private Double stock;

    @Schema(description = "Costo de producir el producto", example = "800")
    private Double costoProduccion;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los parámetros actualizados
    public Producto(Long id, String nombre, Double precio, Double stock, Double costoProduccion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.costoProduccion = costoProduccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getCostoProduccion() {
        return costoProduccion;
    }

    public void setCostoProduccion(Double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }
}