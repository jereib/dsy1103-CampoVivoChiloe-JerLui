package com.example.productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Producto {
    @Id @GeneratedValue
    private Long id;
    private String nombre;
    private Double precio;
    private Double stock;

    public Producto() {
    }

    public Producto(long id, String nombre, Double precio, Double stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock
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
}
