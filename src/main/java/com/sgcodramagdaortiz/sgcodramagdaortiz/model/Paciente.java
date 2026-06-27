package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

// Importa LocalDate para manejar fechas
import java.time.LocalDate;

// Importa las anotaciones de JPA para mapear esta clase a una tabla en la base de datos
import jakarta.persistence.*;

// Importa anotaciones de validación para asegurar la integridad de los datos
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa a un Paciente en el sistema.
 * 
 * Contiene toda la información personal y de contacto del paciente.
 */
@Entity
@Table(name = "paciente")
public class Paciente {

    /**
     * Llave primaria de la tabla paciente.
     * Se genera automáticamente con autoincremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long idPaciente;

    /**
     * Número de documento de identidad del paciente (DNI, CC, pasaporte, etc.).
     * Es obligatorio y debe ser único en toda la tabla.
     */
    @NotBlank(message = "El documento es obligatorio")
    @Size(max = 50, message = "El documento no puede tener más de 50 caracteres")
    @Column(name = "documento", nullable = false, unique = true)
    private String documento;

    /**
     * Nombre(s) del paciente.
     * Campo obligatorio.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Apellido(s) del paciente.
     * Campo obligatorio.
     */
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    @Column(name = "apellido", nullable = false)
    private String apellido;

    /**
     * Correo electrónico del paciente.
     * Debe tener un formato válido.
     */
    @Email(message = "Correo electrónico inválido")
    @Size(max = 150)
    @Column(name = "correo")
    private String correo;

    /**
     * Número de teléfono de contacto del paciente.
     */
    @Size(max = 25)
    @Column(name = "telefono")
    private String telefono;

    /**
     * Fecha de nacimiento del paciente.
     */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /**
     * Dirección residencial del paciente.
     */
    @Size(max = 250)
    @Column(name = "direccion")
    private String direccion;

    /**
     * Fecha en la que el paciente fue registrado en el sistema.
     */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    /**
     * Constructor vacío obligatorio para JPA (Hibernate).
     */
    public Paciente() {
    }

    // ====================== GETTERS Y SETTERS ======================

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}