package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO utilizado para recibir las credenciales
 * enviadas por el usuario durante el inicio de sesión.
 *
 * Este objeto NO representa una tabla de la base de datos.
 * Su función es transportar los datos del login.
 */
public class LoginRequest {

    /**
     * Nombre de usuario enviado para autenticarse.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    /**
     * Contraseña enviada para autenticarse.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    /**
     * Constructor vacío.
     */
    public LoginRequest() {
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}