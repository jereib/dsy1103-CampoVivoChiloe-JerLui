package com.example.ms_actividades.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el microservicio.
 * Captura errores de validación y los devuelve como respuestas HTTP.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja errores de validación de campos en los request bodies.
     *
     * @param ex excepción de validación lanzada por Spring.
     * @return mapa con los nombres de campo y sus mensajes de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // recorremos los errores de campo y los devolvemos como mapa
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
