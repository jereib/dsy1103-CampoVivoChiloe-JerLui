package com.example.productos.service;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    // Creamos el Logger de SLF4J de forma manual (así no dependemos de Lombok)
    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crearProducto(ProductoRequestDTO dto) {
        // Regla R4: El precio debe ser al menos el costo + 20% de margen
        double precioMinimo = dto.getCostoProduccion() * 1.20;

        if (dto.getPrecio() < precioMinimo) {
            log.error("Error de validación: El precio {} es menor al margen permitido (Mínimo: {})", dto.getPrecio(), precioMinimo);
            // Lanzamos una excepción que luego atraparemos con el @ControllerAdvice
            throw new IllegalArgumentException("El precio no cumple con el margen mínimo del 20% establecido por la cooperativa");
        }

        // 1. Instanciamos la entidad vacía
        Producto producto = new Producto();

        // 2. Mapeamos (pasamos) los datos del DTO a la Entidad
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCostoProduccion(dto.getCostoProduccion());

        log.info("Creando nuevo producto en el catálogo: {}", producto.getNombre());

        // 3. Guardamos en la base de datos
        return repository.save(producto);
    }
}