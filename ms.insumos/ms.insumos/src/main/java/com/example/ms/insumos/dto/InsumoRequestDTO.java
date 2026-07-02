package com.example.ms.insumos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que transporta los datos de un insumo desde las peticiones del cliente.
 * Incluye validaciones para asegurar que los campos obligatorios sean enviados.
 */
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