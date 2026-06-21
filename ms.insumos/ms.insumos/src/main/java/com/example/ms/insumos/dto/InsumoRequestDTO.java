package com.example.ms.insumos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Insumo DTO", description = "Un DTO que permite obtener ciertos atributos de un insumo")
public class InsumoRequestDTO {

    @Schema(description = "Nombre del insumo", example = "Tornillos de acero 1/2")
    @NotBlank(message = "El nombre del insumo es obligatorio")
    private String nombre;

    @Schema(description = "Descripcion sobre el insumo", example = "Caja de 100 unidades de tornillos de alta resistencia")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Cantidad del insumo", example = "50")
    @NotNull(message = "El stock inicial es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Precio unitario del insumo", example = "4000")
    @NotNull(message = "El precio por unidad es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double precioUnidad;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Double getPrecioUnidad() { return precioUnidad; }
    public void setPrecioUnidad(Double precioUnidad) { this.precioUnidad = precioUnidad; }
}