package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

// Importa las clases necesarias para manejar fechas y horas
import java.time.LocalDate;
import java.time.LocalTime;

// Importa las anotaciones de JPA para mapear la clase a la base de datos
import jakarta.persistence.*;

// Importa anotaciones de validación de Bean Validation
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa una cita en el sistema.
 * 
 * Una cita es la agenda de atención entre un paciente y un profesional.
 */
@Entity
@Table(name = "cita")
public class Cita {

    /**
     * Llave primaria de la tabla cita.
     * Se genera automáticamente con autoincremento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    /**
     * Relación ManyToOne con la entidad Paciente.
     * Una cita pertenece a un solo paciente.
     * Es obligatoria.
     */
    @ManyToOne
    @JoinColumn(name = "id_paciente")
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;

    /**
     * ID del profesional que atenderá la cita.
     * (Por ahora se guarda solo el ID, se puede mejorar con una relación)
     */
    @Column(name = "id_profesional")
    private Long idProfesional;

    /**
     * ID del servicio que se va a prestar en la cita.
     * (Por ahora se guarda solo el ID)
     */
    @Column(name = "id_servicio")
    private Long idServicio;

    /**
     * Fecha en la que se realizará la cita.
     * No puede ser nula.
     */
    @NotNull(message = "La fecha de la cita es obligatoria")
    @Column(name = "fecha")
    private LocalDate fecha;

    /**
     * Hora de inicio de la cita.
     * No puede ser nula.
     */
    @NotNull(message = "La hora es obligatoria")
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    /**
     * Duración estimada de la cita en minutos.
     */
    @Column(name = "duracion_min")
    private Integer duracionMin;

    /**
     * Sala o consultorio donde se realizará la cita.
     * Máximo 50 caracteres.
     */
    @Size(max = 50)
    @Column(name = "sala")
    private String sala;

    /**
     * Estado actual de la cita (ej: PROGRAMADA, CONFIRMADA, CANCELADA, etc.).
     * Máximo 30 caracteres.
     */
    @Size(max = 30)
    @Column(name = "estado")
    private String estado;

    /**
     * Observaciones adicionales sobre la cita.
     * Máximo 500 caracteres.
     */
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;

    /**
     * ID del usuario que creó el registro.
     */
    @Column(name = "usuario_crea")
    private Integer usuarioCrea;

    /**
     * Fecha en la que se creó el registro.
     */
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    /**
     * Fecha de la última modificación del registro.
     */
    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    /**
     * Constructor vacío obligatorio para JPA.
     */
    public Cita() {
    }

    // ====================== GETTERS Y SETTERS ======================

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Long idProfesional) {
        this.idProfesional = idProfesional;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(Integer duracionMin) {
        this.duracionMin = duracionMin;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(Integer usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}