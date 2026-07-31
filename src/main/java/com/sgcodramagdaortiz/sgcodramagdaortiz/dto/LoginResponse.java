package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

/**
 * DTO utilizado para enviar la respuesta
 * después de una autenticación exitosa.
 *
 * Contiene el token JWT y los datos básicos
 * necesarios para identificar al usuario autenticado.
 */
public class LoginResponse {

    /**
     * Token JWT generado después del login.
     */
    private String token;

    /**
     * Nombre de usuario autenticado.
     */
    private String username;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    /**
     * Rol asignado al usuario.
     */
    private String rol;

    /**
     * Constructor vacío.
     */
    public LoginResponse() {
    }

    /**
     * Constructor completo.
     *
     * @param token token JWT
     * @param username nombre de usuario
     * @param nombre nombre
     * @param apellido apellido
     * @param rol rol del usuario
     */
    public LoginResponse(
            String token,
            String username,
            String nombre,
            String apellido,
            String rol) {

        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}