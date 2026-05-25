package com.example.ms.ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VentaRequestDTO {
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Debe vender al menos 1 unidad")
    private Integer cantidad;

    @NotBlank(message = "Debe especificar el canal (FISICO u ONLINE)")
    private String canal;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
}