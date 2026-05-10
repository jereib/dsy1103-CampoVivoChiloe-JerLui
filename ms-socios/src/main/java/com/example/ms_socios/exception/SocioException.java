package com.example.ms_socios.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//se encarga de mostrar los mensajes de error de @NotNull y @NotBlank
@RestControllerAdvice
public class SocioException {
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
