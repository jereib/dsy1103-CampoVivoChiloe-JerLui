package com.example.ms.ventas.exception; // Ojo con tu nombre de paquete

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el microservicio de ventas.
 * Captura errores de validación y reglas de negocio, devolviendo
 * respuestas adecuadas al cliente.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de reglas de negocio como stock insuficiente o producto inexistente.
     *
     * @param ex excepción lanzada por la lógica de negocio
     * @return respuesta con código 400 y detalle del error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRuleException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Error de Regla de Negocio");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura errores de validación de los DTO y los devuelve agrupados campo por campo.
     *
     * @param ex excepción de validación
     * @return respuesta con código 400 y los errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}