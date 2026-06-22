package com.example.ms.insumos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Schema(name = "Insumo", description = "Representa un insumo en el sistema")
@Entity
@Table(name = "insumos")
public class Insumo {

    @Schema(description = "Identificador único", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del insumo", example = "Tornillos de acero 1/2")
    private String nombre;

    @Schema(description = "Descripcion sobre el insumo", example = "Caja de 100 unidades de tornillos de alta resistencia")
    private String descripcion;

    @Schema(description = "Cantidad del insumo", example = "50")
    private Integer stock;

    @Schema(description = "Precio unitario del insumo", example = "4000")
    private Double precioUnidad;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Double getPrecioUnidad() { return precioUnidad; }
    public void setPrecioUnidad(Double precioUnidad) { this.precioUnidad = precioUnidad; }
}