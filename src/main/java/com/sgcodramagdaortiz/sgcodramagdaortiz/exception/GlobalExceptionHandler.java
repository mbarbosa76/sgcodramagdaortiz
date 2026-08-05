package com.sgcodramagdaortiz.sgcodramagdaortiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones de la API REST.
 *
 * Permite centralizar las respuestas de error
 * generadas por los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de recurso no encontrado.
     *
     * @param exception excepción generada
     * @return respuesta HTTP 404
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<String> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(AutenticacionException.class)
public ResponseEntity<String> manejarAutenticacion(
        AutenticacionException exception) {


    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());

}
}