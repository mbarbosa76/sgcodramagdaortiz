package com.sgcodramagdaortiz.sgcodramagdaortiz.exception;

/**
 * Excepción personalizada utilizada cuando un recurso
 * solicitado no existe en la base de datos.
 *
 * Por ejemplo:
 * - Usuario inexistente.
 * - Paciente inexistente.
 * - Cita inexistente.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    /**
     * Constructor que recibe el mensaje de error.
     *
     * @param mensaje descripción del recurso que no fue encontrado
     */
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}