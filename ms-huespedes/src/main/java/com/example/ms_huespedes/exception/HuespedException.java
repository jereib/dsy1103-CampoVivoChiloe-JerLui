package com.example.ms_huespedes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador de excepciones para errores de validación de huéspedes.
 * Atrapa errores de validación y los devuelve como un mapa campo -> mensaje de error.
 */
@RestControllerAdvice
public class HuespedException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    /**
     * Procesa errores de validación de los campos de Huesped.
     *
     * @param ex excepción lanzada por validación fallida.
     * @return mapa con los nombres de campo y sus mensajes de error.
     */
    public ResponseEntity<Map<String, String>>
    manejarErrores(MethodArgumentNotValidException ex){

        Map<String, String> errores =
                new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errores.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );

                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }
}
