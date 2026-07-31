package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

// Importa LocalDate para manejar la fecha de creación del usuario
import java.time.LocalDate;

// Importa las anotaciones de JPA para mapear la clase con MySQL
import jakarta.persistence.*;

// Importa anotaciones de validación
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un Usuario del sistema.
 *
 * Se encuentra relacionada con la tabla "usuario"
 * de la base de datos sgco_dramagdaortiz.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /**
     * Llave primaria de la tabla usuario.
     * Se genera automáticamente mediante AUTO_INCREMENT.
     *
     * IMPORTANTE:
     * Se utiliza Integer (no Long) porque la columna
     * en MySQL está definida como INT UNSIGNED.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * Nombre de usuario utilizado para iniciar sesión.
     * Debe ser único y obligatorio.
     */
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Contraseña almacenada como hash.
     *
     * IMPORTANTE:
     * Nunca se debe guardar la contraseña directamente.
     * Se utiliza BCrypt para generar y verificar este hash.
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Nombre del usuario.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Apellido del usuario.
     */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    @Column(name = "apellido", nullable = false)
    private String apellido;

    /**
     * Correo electrónico del usuario.
     * Debe tener un formato válido.
     */
    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    @Column(name = "correo", nullable = false, unique = true)
    private String correo;

    /**
     * Número telefónico del usuario.
     */
    @Size(max = 25, message = "El teléfono no puede superar los 25 caracteres")
    @Column(name = "telefono")
    private String telefono;

    /**
     * Rol del usuario.
     *
     * En MySQL la columna utiliza:
     * Recepcionista, Profesional y Admin.
     *
     * Se utiliza String para conservar exactamente
     * los valores existentes en la base de datos.
     */
    @Column(name = "rol", nullable = false)
    private String rol;

    /**
     * Fecha de creación del usuario.
     */
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    /**
     * Constructor vacío obligatorio para JPA/Hibernate.
     */
    public Usuario() {
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}