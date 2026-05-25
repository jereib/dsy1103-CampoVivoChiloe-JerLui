package com.example.productos.service;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crearProducto(ProductoRequestDTO dto) {
        double precioMinimo = dto.getCostoProduccion() * 1.20;

        if (dto.getPrecio() < precioMinimo) {
            log.error("Error de validación: El precio {} es menor al margen permitido (Mínimo: {})", dto.getPrecio(), precioMinimo);
            throw new IllegalArgumentException("El precio no cumple con el margen mínimo del 20% establecido por la cooperativa");
        }

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCostoProduccion(dto.getCostoProduccion());

        log.info("Creando nuevo producto en el catálogo: {}", producto.getNombre());
        return repository.save(producto);
    }

    public Producto obtenerProductoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public Producto actualizarProducto(Long id, ProductoRequestDTO dto) {
        log.info("Iniciando actualización de producto con ID: {}", id);
        Producto producto = obtenerProductoPorId(id);

        double precioMinimo = dto.getCostoProduccion() * 1.20;
        if (dto.getPrecio() < precioMinimo) {
            log.error("Error de validación al actualizar: El precio {} es menor al margen permitido (Mínimo: {})", dto.getPrecio(), precioMinimo);
            throw new IllegalArgumentException("El precio no cumple con el margen mínimo del 20% establecido por la cooperativa");
        }

        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCostoProduccion(dto.getCostoProduccion());

        log.info("Producto con ID {} actualizado exitosamente", id);
        return repository.save(producto);
    }

    public void eliminarProducto(Long id) {
        log.info("Iniciando eliminación de producto con ID: {}", id);
        Producto producto = obtenerProductoPorId(id);
        repository.delete(producto);
        log.info("Producto con ID {} eliminado exitosamente", id);
    }
}