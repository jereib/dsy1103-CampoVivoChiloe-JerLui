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
import java.util.List;

/**
 * Servicio que contiene la lógica de negocio para la gestión de ventas.
 * Valida stock contra el microservicio de productos y realiza el registro
 * de las ventas en la base de datos.
 */
@Service
public class VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);
    private final VentaRepository repository;
    private final ProductoClient productoClient;

    public VentaService(VentaRepository repository, ProductoClient productoClient) {
        this.repository = repository;
        this.productoClient = productoClient;
    }

    /**
     * Registra una nueva venta validando que el producto exista en el catálogo
     * y que tenga stock suficiente.
     *
     * @param dto datos de la venta a registrar
     * @return la venta guardada con su total calculado
     * @throws IllegalArgumentException si el producto no existe o el stock es insuficiente
     */
    public Venta registrarVenta(VentaRequestDTO dto) {
        log.info("Iniciando registro de venta para el producto ID: {}", dto.getProductoId());

        ProductoDTO productoExistente;
        try {
            // Llama al ms-productos para verificar que el producto existe
            productoExistente = productoClient.obtenerProductoPorId(dto.getProductoId());
        } catch (Exception e) {
            log.error("Error al contactar ms-productos o producto no encontrado");
            throw new IllegalArgumentException("El producto con ID " + dto.getProductoId() + " no existe o el servicio no responde.");
        }

        // Valida que haya suficiente stock disponible
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
    /**
     * Devuelve una lista con todas las ventas registradas.
     *
     * @return lista de ventas
     */
    public List<Venta> listarTodas() {
        return repository.findAll();
    }

    /**
     * Busca una venta por su id. Lanza una excepción si no existe.
     *
     * @param id identificador de la venta
     * @return la venta encontrada
     * @throws IllegalArgumentException si no se encuentra ninguna venta con ese id
     */
    public Venta obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada con ID: " + id));
    }

    /**
     * Actualiza la cantidad y el canal de una venta existente.
     *
     * @param id  identificador de la venta a actualizar
     * @param dto datos nuevos (cantidad y canal)
     * @return la venta actualizada
     * @throws IllegalArgumentException si la venta no existe
     */
    public Venta actualizarVenta(Long id, VentaRequestDTO dto) {
        log.info("Actualizando venta con ID: {}", id);
        Venta venta = obtenerPorId(id);

        venta.setCantidad(dto.getCantidad());
        venta.setCanal(dto.getCanal());

        return repository.save(venta);
    }

    /**
     * Elimina una venta del sistema según su id.
     *
     * @param id identificador de la venta a eliminar
     * @throws IllegalArgumentException si la venta no existe
     */
    public void eliminarVenta(Long id) {
        log.info("Eliminando venta con ID: {}", id);
        Venta venta = obtenerPorId(id);
        repository.delete(venta);
    }

    /**
     * Actualiza solo los campos enviados en el mapa (PATCH).
     * Si se modifica la cantidad, recalcula el total y valida stock.
     *
     * @param id     identificador de la venta
     * @param campos mapa con los campos a actualizar
     * @return la venta con los cambios aplicados
     * @throws IllegalArgumentException si la venta no existe, el stock es insuficiente
     *                                  o el servicio de productos no responde
     */
    public Venta actualizarParcial(Long id, java.util.Map<String, Object> campos) {
        log.info("Iniciando actualización parcial (PATCH) para la venta con ID: {}", id);

        Venta ventaActual = obtenerPorId(id);

        final boolean[] cantidadModificada = {false};

        // Itera sobre los campos enviados y aplica solo los que corresponden
        campos.forEach((clave, valor) -> {
            switch (clave) {
                case "cantidad":
                    if (valor != null) {
                        ventaActual.setCantidad(Integer.valueOf(valor.toString()));
                        cantidadModificada[0] = true;
                    }
                    break;
                case "canal":
                    if (valor != null) {
                        ventaActual.setCanal(valor.toString());
                    }
                    break;
                case "productoId":
                    log.warn("Se ignoró el intento de modificar el productoId. El producto de una venta es inmutable.");
                    break;
                default:
                    log.warn("Campo no reconocido o no permitido para edición en PATCH: [{}]", clave);
                    break;
            }
        });

        if (cantidadModificada[0]) {
            log.info("La cantidad ha sido modificada. Recalculando total y validando stock con ms-productos...");
            ProductoDTO productoExistente;
            try {
                productoExistente = productoClient.obtenerProductoPorId(ventaActual.getProductoId());
            } catch (Exception e) {
                log.error("Error de comunicación con ms-productos al actualizar venta: {}", e.getMessage());
                throw new IllegalArgumentException("No se pudo verificar el stock del producto. Servicio no responde.");
            }

            if (productoExistente.getStock() < ventaActual.getCantidad()) {
                log.warn("Actualización rechazada: Stock insuficiente. Requerido: {}, Disponible: {}",
                        ventaActual.getCantidad(), productoExistente.getStock());
                throw new IllegalArgumentException("Stock insuficiente. Stock actual: " + productoExistente.getStock());
            }

            // Recalculamos el total automáticamente
            ventaActual.setTotal(productoExistente.getPrecio() * ventaActual.getCantidad());
            log.info("Nuevo total recalculado: {}", ventaActual.getTotal());
        }

        return repository.save(ventaActual);
    }
}