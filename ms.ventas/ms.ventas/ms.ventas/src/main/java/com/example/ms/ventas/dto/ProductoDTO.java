package com.example.ms.ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para mapear la respuesta del microservicio de productos.
 * Contiene la información básica de un producto: id, nombre, precio y stock.
 */
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

    /**
     * Devuelve el id del producto.
     */
    public Long getId() { return id; }
    /**
     * Asigna el id del producto.
     */
    public void setId(Long id) { this.id = id; }
    /**
     * Devuelve el nombre del producto.
     */
    public String getNombre() { return nombre; }
    /**
     * Asigna el nombre del producto.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Devuelve el precio del producto.
     */
    public Double getPrecio() { return precio; }
    /**
     * Asigna el precio del producto.
     */
    public void setPrecio(Double precio) { this.precio = precio; }
    /**
     * Devuelve el stock disponible del producto.
     */
    public Double getStock() { return stock; }
    /**
     * Asigna el stock disponible del producto.
     */
    public void setStock(Double stock) { this.stock = stock; }
}