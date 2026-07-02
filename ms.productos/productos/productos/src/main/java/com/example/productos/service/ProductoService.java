package com.example.productos.service;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

// Lógica de negocio para la gestión de productos
@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    // Crea un producto validando que el precio tenga al menos 20% de margen
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

    // Busca un producto por id, lanza error si no existe
    public Producto obtenerProductoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    // Actualiza todos los campos de un producto existente
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

    // Elimina un producto por su id
    public void eliminarProducto(Long id) {
        log.info("Iniciando eliminación de producto con ID: {}", id);
        Producto producto = obtenerProductoPorId(id);
        repository.delete(producto);
        log.info("Producto con ID {} eliminado exitosamente", id);
    }

    // Actualiza solo los campos que vienen en el mapa (PATCH)
    public Producto actualizarParcial(Long id, java.util.Map<String, Object> campos) {
        log.info("Iniciando actualización parcial (PATCH) para el producto con ID: {}", id);

        Producto productoActual = obtenerProductoPorId(id);

        // Itera sobre los campos enviados y aplica solo los que corresponden
        campos.forEach((clave, valor) -> {
            switch (clave) {
                case "nombre":
                    if (valor != null) {
                        productoActual.setNombre(valor.toString());
                    }
                    break;
                case "precio":
                    if (valor != null) {
                        productoActual.setPrecio(Double.parseDouble(valor.toString()));
                    }
                    break;
                case "stock":
                    if (valor != null) {
                        productoActual.setStock(Double.parseDouble(valor.toString()));
                    }
                    break;
                case "costoProduccion":
                    if (valor != null) {
                        productoActual.setCostoProduccion(Double.parseDouble(valor.toString()));
                    }
                    break;
                default:
                    log.warn("Campo no reconocido o ignorado en actualización parcial: [{}]", clave);
                    break;
            }
        });

        // Vuelve a validar el margen por si se modificó precio o costo
        double precioMinimo = productoActual.getCostoProduccion() * 1.20;
        if (productoActual.getPrecio() < precioMinimo) {
            log.error("Error de validación en PATCH: El precio actual de {} es menor al margen permitido (Mínimo requerido: {})",
                    productoActual.getPrecio(), precioMinimo);
            throw new IllegalArgumentException("El precio no cumple con el margen mínimo del 20% establecido por la cooperativa");
        }

        log.info("Producto con ID {} modificado parcialmente de forma exitosa", id);
        return repository.save(productoActual);
    }
}