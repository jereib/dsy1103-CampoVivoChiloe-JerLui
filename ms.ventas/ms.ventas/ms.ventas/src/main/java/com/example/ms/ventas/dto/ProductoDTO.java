package com.example.ms.ventas.dto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private Double precio;
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