package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * ============================================================
 * CITA.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Entidad que representa una cita odontológica.
 *
 * Una cita relaciona:
 *
 * - Un paciente.
 * - Un profesional.
 * - Un servicio odontológico.
 *
 * Estas relaciones permitirán posteriormente integrar:
 *
 * - Historia Clínica.
 * - Facturación.
 * - Reportes.
 *
 * ============================================================
 */

@Entity
@Table(name = "cita")
public class Cita {


    /**
     * Identificador único de la cita.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;



    /**
     * Paciente que solicita la atención.
     *
     * Relación:
     *
     * Paciente 1 ---- N Citas
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "id_paciente",
        nullable = false
    )
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;



    /**
     * Profesional encargado de atender la cita.
     *
     * Relación:
     *
     * Profesional 1 ---- N Citas
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "id_profesional",
        nullable = false
    )
    @NotNull(message = "El profesional es obligatorio")
    private Profesional profesional;



    /**
     * Servicio odontológico asociado.
     *
     * Ejemplo:
     *
     * - Limpieza dental.
     * - Extracción.
     * - Ortodoncia.
     *
     * Relación:
     *
     * Servicio 1 ---- N Citas
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
        name = "id_servicio",
        nullable = false
    )
    @NotNull(message = "El servicio es obligatorio")
    private Servicio servicio;



    /**
     * Fecha programada de la cita.
     */
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha")
    private LocalDate fecha;



    /**
     * Hora de inicio.
     */
    @NotNull(message = "La hora es obligatoria")
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;



    /**
     * Duración en minutos.
     *
     * Este valor será tomado automáticamente
     * desde el servicio seleccionado.
     */
    @Column(name = "duracion_min")
    private Integer duracionMin;



    /**
     * Consultorio asignado.
     */
    @Size(max = 50)
    @Column(name = "sala")
    private String sala;



    /**
     * Estado actual:
     *
     * PROGRAMADA
     * CONFIRMADA
     * CANCELADA
     * ASISTIDA
     */
    @Size(max = 30)
    @Column(name = "estado")
    private String estado;



    /**
     * Observaciones adicionales.
     */
    @Size(max = 500)
    @Column(name = "observacion")
    private String observacion;



    /**
     * Usuario que creó la cita.
     */
    @Column(name = "usuario_crea")
    private Integer usuarioCrea;



    /**
     * Fecha creación.
     */
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;



    /**
     * Fecha modificación.
     */
    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;



    /**
     * Constructor vacío requerido por JPA.
     */
    public Cita() {
    }



    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================


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


    public Profesional getProfesional() {
        return profesional;
    }


    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }


    public Servicio getServicio() {
        return servicio;
    }


    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
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