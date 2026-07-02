package com.example.ms.insumos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * Entidad que representa un insumo agrícola en la base de datos.
 * Mapea la tabla "insumos" con sus atributos principales.
 */
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

    /**
     * @return el identificador único del insumo
     */
    public Long getId() { return id; }

    /**
     * @param id identificador único a asignar
     */
    public void setId(Long id) { this.id = id; }

    /**
     * @return el nombre del insumo
     */
    public String getNombre() { return nombre; }

    /**
     * @param nombre nombre del insumo a asignar
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * @return la descripción del insumo
     */
    public String getDescripcion() { return descripcion; }

    /**
     * @param descripcion descripción a asignar
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * @return la cantidad en stock del insumo
     */
    public Integer getStock() { return stock; }

    /**
     * @param stock cantidad en stock a asignar
     */
    public void setStock(Integer stock) { this.stock = stock; }

    /**
     * @return el precio unitario del insumo
     */
    public Double getPrecioUnidad() { return precioUnidad; }

    /**
     * @param precioUnidad precio unitario a asignar
     */
    public void setPrecioUnidad(Double precioUnidad) { this.precioUnidad = precioUnidad; }
}