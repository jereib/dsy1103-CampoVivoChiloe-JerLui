package com.example.productos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un producto agrícola almacenado en la base de datos.
 * Contiene información como nombre, precio, stock y costo de producción.
 */
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

    /**
     * Constructor vacío requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los atributos del producto.
     *
     * @param id              identificador único
     * @param nombre          nombre del producto
     * @param precio          precio de venta
     * @param stock           cantidad disponible
     * @param costoProduccion costo de producción
     */
    public Producto(Long id, String nombre, Double precio, Double stock, Double costoProduccion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.costoProduccion = costoProduccion;
    }

    /**
     * @return el id del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id nuevo identificador para el producto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return el nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre nuevo nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return el precio de venta del producto
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio nuevo precio de venta
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return la cantidad disponible en stock
     */
    public Double getStock() {
        return stock;
    }

    /**
     * @param stock nueva cantidad en stock
     */
    public void setStock(Double stock) {
        this.stock = stock;
    }

    /**
     * @return el costo de producción del producto
     */
    public Double getCostoProduccion() {
        return costoProduccion;
    }

    /**
     * @param costoProduccion nuevo costo de producción
     */
    public void setCostoProduccion(Double costoProduccion) {
        this.costoProduccion = costoProduccion;
    }
}