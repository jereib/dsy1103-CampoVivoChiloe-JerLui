package com.example.ms.ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para recibir los datos de una venta desde el cliente.
 * Contiene el id del producto, la cantidad y el canal de venta.
 */
@Schema(name = "Venta DTO", description = "Un DTO que permite obtener ciertos atributos de la venta en ms-ventas")
public class VentaRequestDTO {

    @Schema(description = "Id del producto", example = "1")
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Schema(description = "Cantidad de productos en la venta", example = "15")
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe vender al menos 1 unidad")
    private Integer cantidad;

    @Schema(description = "Medio o plataforma donde se realizo la venta", example = "WEB")
    @NotBlank(message = "Debe especificar el canal (FISICO u ONLINE)")
    private String canal;

    /**
     * Devuelve el id del producto a vender.
     */
    public Long getProductoId() { return productoId; }
    /**
     * Asigna el id del producto a vender.
     */
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    /**
     * Devuelve la cantidad de productos a vender.
     */
    public Integer getCantidad() { return cantidad; }
    /**
     * Asigna la cantidad de productos a vender.
     */
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    /**
     * Devuelve el canal por el que se realiza la venta.
     */
    public String getCanal() { return canal; }
    /**
     * Asigna el canal por el que se realiza la venta.
     */
    public void setCanal(String canal) { this.canal = canal; }
}