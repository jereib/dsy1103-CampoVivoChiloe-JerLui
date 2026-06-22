package com.example.ms.ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Producto DTO", description = "Un DTO que permite obtener ciertos atributos de un producto en ms-productos")
public class ProductoDTO {
    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Pan")
    private String nombre;

    @Schema(description = "Precio del producto", example = "1200")
    private Double precio;

    @Schema(description = "Cantidad del producto", example = "10")
    private Double stock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Double getStock() { return stock; }
    public void setStock(Double stock) { this.stock = stock; }
}