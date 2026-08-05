package com.sgcodramagdaortiz.sgcodramagdaortiz.exception;


/**
 * ============================================================
 * AUTENTICACIONEXCEPTION.JAVA
 * ============================================================
 *
 * Excepción utilizada cuando las credenciales
 * de inicio de sesión son incorrectas.
 *
 * ============================================================
 */

public class AutenticacionException
        extends RuntimeException {


    public AutenticacionException(
            String mensaje) {


        super(mensaje);

    }

}