package com.example.ms.ventas.service;

import com.example.ms.ventas.client.ProductoClient;
import com.example.ms.ventas.dto.ProductoDTO;
import com.example.ms.ventas.dto.VentaRequestDTO;
import com.example.ms.ventas.model.Venta;
import com.example.ms.ventas.repository.VentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    private final VentaRepository repository;
    private final ProductoClient productoClient;

    public VentaService(VentaRepository repository, ProductoClient productoClient) {
        this.repository = repository;
        this.productoClient = productoClient;
    }

    public Venta registrarVenta(VentaRequestDTO dto) {
        log.info("Iniciando registro de venta para el producto ID: {}", dto.getProductoId());

        ProductoDTO productoExistente;
        try {
            // Llama al otro microservicio
            productoExistente = productoClient.obtenerProductoPorId(dto.getProductoId());
        } catch (Exception e) {
            log.error("Error al contactar ms-productos o producto no encontrado");
            throw new IllegalArgumentException("El producto con ID " + dto.getProductoId() + " no existe o el servicio no responde.");
        }

        // Valida el stock
        if (productoExistente.getStock() < dto.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente. Stock actual: " + productoExistente.getStock());
        }

        Venta venta = new Venta();
        venta.setProductoId(productoExistente.getId());
        venta.setCantidad(dto.getCantidad());
        venta.setCanal(dto.getCanal());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setTotal(productoExistente.getPrecio() * dto.getCantidad());

        log.info("Venta calculada con éxito. Total: {}", venta.getTotal());
        return repository.save(venta);
    }
}