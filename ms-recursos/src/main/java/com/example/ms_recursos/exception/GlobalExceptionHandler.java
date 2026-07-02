package com.example.ms_recursos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para el microservicio.
 * Captura errores de validacion y los retorna como JSON con codigo 400.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja errores de validacion de jakarta validation.
     * Agrupa los errores campo por campo y los devuelve en un mapa.
     *
     * @param ex excepcion de validacion
     * @return mapa con los errores por campo y estado 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
