package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import com.example.productos.dto.ProductoRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Controlador REST que expone los endpoints para gestionar productos.
 * Soporta operaciones CRUD y actualización parcial de productos.
 */
@RestController
@RequestMapping("/api/v1/productos")
@Tag( name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    private final ProductoService service;

    /**
     * Constructor que inyecta el servicio de productos.
     *
     * @param service lógica de negocio para productos
     */
    public ProductoController(ProductoService service) {
        this.service = service;
    }

    /**
     * Crea un nuevo producto a partir de los datos enviados en la solicitud.
     *
     * @param dto datos del producto a crear
     * @return el producto creado con estado 201
     */
    @PostMapping
    @Operation( summary = "Crear producto", description = "Permite crear un producto de acuerdo a sus atributos" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        Producto nuevoProducto = service.crearProducto(dto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    /**
     * Obtiene un producto registrado por su identificador único.
     *
     * @param id identificador del producto
     * @return el producto encontrado con estado 200
     */
    @GetMapping("/{id}")
    @Operation( summary = "Obtener producto por id", description = "Obtiene un producto registrado mediante su id" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto producto = service.obtenerProductoPorId(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    /**
     * Lista todos los productos registrados en el sistema.
     *
     * @return lista de productos con estado 200
     */
    @GetMapping
    @Operation( summary = "Listar productos", description = "Obtiene todos los productos registrados" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<List<Producto>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    /**
     * Actualiza todos los campos de un producto existente.
     *
     * @param id  identificador del producto a actualizar
     * @param dto nuevos datos del producto
     * @return el producto actualizado con estado 200
     */
    @PutMapping("/{id}")
    @Operation( summary = "Actualizar producto", description = "Permite actualizar un producto mediante su id" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        Producto productoActualizado = service.actualizarProducto(id, dto);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    /**
     * Elimina un producto del sistema por su id.
     *
     * @param id identificador del producto a eliminar
     * @return estado 204 sin contenido
     */
    @DeleteMapping("/{id}")
    @Operation( summary = "Eliminar producto", description = "Elimina un producto registrado mediante su id" )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Consulta exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autenticado"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acceso denegado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Actualiza solo los campos enviados en el cuerpo de la petición.
     * Si el precio o costo cambian, se valida el margen mínimo del 20%.
     *
     * @param id     identificador del producto
     * @param campos mapa con los campos a modificar
     * @return el producto actualizado o un mensaje de error
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un producto", description = "Permite modificar únicamente los atributos enviados en el cuerpo de la petición utilizando el id del producto")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Actualización parcial exitosa"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida o margen de precio incorrecto"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Producto no encontrado"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor"
            )
    })
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody java.util.Map<String, Object> campos) {
        try {
            Producto productoParcial = service.actualizarParcial(id, campos);
            return new ResponseEntity<>(productoParcial, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}