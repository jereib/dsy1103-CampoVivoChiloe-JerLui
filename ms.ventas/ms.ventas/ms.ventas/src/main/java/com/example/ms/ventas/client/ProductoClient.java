package com.example.ms.ventas.client;

import com.example.ms.ventas.dto.ProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con el microservicio de productos.
 * Permite obtener información de productos desde ms-productos.
 */
@FeignClient(name = "ms-productos", url = "${ms-productos.url:http://localhost:8086/api/v1/productos}")
public interface ProductoClient {
    /**
     * Obtiene un producto del catálogo según su id.
     *
     * @param id identificador del producto
     * @return datos del producto encontrado
     */
    @GetMapping("/{id}")
    ProductoDTO obtenerProductoPorId(@PathVariable("id") Long id);
}