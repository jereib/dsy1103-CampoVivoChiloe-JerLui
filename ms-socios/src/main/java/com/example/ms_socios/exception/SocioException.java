package com.example.ms_socios.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
/**
 * Manejador global de excepciones para el microservicio.
 * Captura errores de validación y los devuelve como un mapa campo -> mensaje.
 */
public class SocioException {
    /**
     * Maneja errores de validación de campos en los request body.
     *
     * @param ex excepción lanzada cuando falla la validación con @Valid
     * @return mapa con los nombres de los campos y sus mensajes de error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
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
