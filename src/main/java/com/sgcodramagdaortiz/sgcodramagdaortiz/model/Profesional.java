package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

// Importa las anotaciones de JPA (para mapear la clase a una tabla de MySQL)
import jakarta.persistence.*;

// Importa las anotaciones de validación
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Importa LocalDate para la fecha de registro
import java.time.LocalDate;

/**
 * ============================================================
 * PROFESIONAL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Entidad que representa a un profesional (odontólogo,
 * higienista, especialista, etc.) del sistema.
 *
 * Se mapea a la tabla "profesional" en MySQL.
 *
 * Campos "departamento" y "municipio":
 *
 * Se agregaron para poder guardar la ubicación del
 * profesional seleccionada mediante la API pública externa
 * "API Colombia" (https://api-colombia.com), consumida a
 * través de ApiColombiaController / ApiColombiaService en
 * este mismo backend.
 */
@Entity
@Table(name = "profesional")
public class Profesional {

    /**
     * Identificador interno (autoincremental).
     * NO es el número de cédula/identificación del profesional,
     * ese se guarda en el campo "identificacion".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProfesional;

    /**
     * Número de identificación (cédula) del profesional.
     * Debe ser único: dos profesionales no pueden compartir
     * el mismo número de identificación.
     */
    @NotBlank
    @Column(unique = true)
    private String identificacion;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String especialidad;

    @NotBlank
    private String telefono;

    /**
     * Correo del profesional.
     * Debe tener formato válido y ser único en el sistema.
     */
    @Email
    @NotBlank
    @Column(unique = true)
    private String correo;

    private String direccion;

    /**
     * Número de registro profesional / tarjeta profesional
     * (por ejemplo, el registro ante el Colegio Odontológico).
     */
    @Column(name = "registro_profesional")
    private String registroProfesional;

    /**
     * Estado del profesional dentro del sistema.
     * Valores esperados: "Activo" / "Inactivo".
     */
    private String estado = "Activo";

    /**
     * Departamento de Colombia donde ejerce el profesional.
     * Se llena seleccionando un valor obtenido desde la
     * API pública externa "API Colombia".
     */
    private String departamento;

    /**
     * Ciudad/municipio de Colombia donde ejerce el profesional.
     * Se llena seleccionando un valor obtenido desde la
     * API pública externa "API Colombia", filtrado según
     * el departamento elegido.
     */
    private String municipio;

    /**
     * Fecha en la que se registró el profesional en el sistema.
     * Se asigna automáticamente en ProfesionalServiceImpl.
     */
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;


    /*
     * ============================================================
     * CONSTRUCTORES
     * ============================================================
     */

    public Profesional() {
    }

    public Profesional(Long idProfesional, String identificacion, String nombre, String apellido,
            String especialidad, String telefono, String correo, String direccion,
            String registroProfesional, String estado, String departamento,
            String municipio, LocalDate fechaRegistro) {

        this.idProfesional = idProfesional;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.registroProfesional = registroProfesional;
        this.estado = estado;
        this.departamento = departamento;
        this.municipio = municipio;
        this.fechaRegistro = fechaRegistro;
    }


    /*
     * ============================================================
     * GETTERS Y SETTERS
     * ============================================================
     */

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRegistroProfesional() {
        return registroProfesional;
    }

    public void setRegistroProfesional(String registroProfesional) {
        this.registroProfesional = registroProfesional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}