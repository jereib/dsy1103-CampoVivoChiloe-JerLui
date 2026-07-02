package com.example.ms_liquidaciones.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el microservicio.
 * Captura errores de validación y los devuelve como un mapa de campos y mensajes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja errores de validación de argumentos.
     *
     * @param ex excepción de validación.
     * @return mapa con los campos inválidos y sus mensajes de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
