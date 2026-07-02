package com.example.ms_hospedajes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el microservicio.
 * Atrapa errores de validación de {@code @Valid} y los devuelve como JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de argumentos de métodos anotados con {@code @Valid}.
     *
     * @param ex excepción de validación lanzada por Spring.
     * @return mapa con los nombres de los campos y sus mensajes de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
