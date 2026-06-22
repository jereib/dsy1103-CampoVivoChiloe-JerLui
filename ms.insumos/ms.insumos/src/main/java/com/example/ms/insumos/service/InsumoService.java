package com.example.ms.insumos.service;

import com.example.ms.insumos.dto.InsumoRequestDTO;
import com.example.ms.insumos.model.Insumo;
import com.example.ms.insumos.repository.InsumoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsumoService {

    private static final Logger log = LoggerFactory.getLogger(InsumoService.class);
    private final InsumoRepository repository;

    public InsumoService(InsumoRepository repository) {
        this.repository = repository;
    }

    public Insumo crearInsumo(InsumoRequestDTO dto) {
        log.info("Iniciando creación de nuevo insumo agrícola: {}", dto.getNombre());
        Insumo insumo = new Insumo();
        insumo.setNombre(dto.getNombre());
        insumo.setDescripcion(dto.getDescripcion());
        insumo.setStock(dto.getStock());
        insumo.setPrecioUnidad(dto.getPrecioUnidad());

        Insumo guardado = repository.save(insumo);
        log.info("Insumo creado exitosamente con ID: {}", guardado.getId());
        return guardado;
    }

    public Insumo obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con ID: " + id));
    }

    public List<Insumo> listarTodos() {
        return repository.findAll();
    }

    public Insumo actualizarInsumo(Long id, InsumoRequestDTO dto) {
        log.info("Actualizando insumo con ID: {}", id);
        Insumo insumo = obtenerPorId(id); // Reutilizamos el método para validar que existe

        insumo.setNombre(dto.getNombre());
        insumo.setDescripcion(dto.getDescripcion());
        insumo.setStock(dto.getStock());
        insumo.setPrecioUnidad(dto.getPrecioUnidad());

        return repository.save(insumo);
    }

    public void eliminarInsumo(Long id) {
        log.info("Eliminando insumo con ID: {}", id);
        Insumo insumo = obtenerPorId(id);
        repository.delete(insumo);
    }
}