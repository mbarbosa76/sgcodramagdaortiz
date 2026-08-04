package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * ============================================================
 * HISTORIACLINICA.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Entidad que representa la historia clínica odontológica
 * de un paciente.
 *
 * Registra la información generada durante la atención:
 *
 * - Motivo de consulta.
 * - Diagnóstico.
 * - Tratamiento realizado.
 * - Observaciones.
 *
 * Relaciones:
 *
 * Paciente 1 ---- N HistoriaClinica
 * Profesional 1 ---- N HistoriaClinica
 * Cita 1 ---- 1 HistoriaClinica
 *
 * ============================================================
 */

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {


    /**
     * Identificador único.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historia")
    private Long idHistoria;



    /**
     * Paciente asociado a la historia clínica.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_paciente",
            nullable = false
    )
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;



    /**
     * Profesional que realizó la atención.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_profesional",
            nullable = false
    )
    @NotNull(message = "El profesional es obligatorio")
    private Profesional profesional;



    /**
     * Cita relacionada con la atención.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_cita",
            nullable = false
    )
    @NotNull(message = "La cita es obligatoria")
    private Cita cita;



    /**
     * Fecha de atención.
     */
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha")
    private LocalDate fecha;



    /**
     * Motivo principal de consulta.
     */
    @NotBlank(message = "El motivo de consulta es obligatorio")
    @Column(name = "motivo_consulta", length = 500)
    private String motivoConsulta;



    /**
     * Diagnóstico realizado.
     */
    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(name = "diagnostico", length = 1000)
    private String diagnostico;



    /**
     * Tratamiento aplicado.
     */
    @Column(name = "tratamiento", length = 1000)
    private String tratamiento;



    /**
     * Observaciones adicionales.
     */
    @Column(name = "observaciones", length = 1000)
    private String observaciones;



    /**
     * Constructor vacío requerido por JPA.
     */
    public HistoriaClinica() {
    }



    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================


    public Long getIdHistoria() {
        return idHistoria;
    }


    public void setIdHistoria(Long idHistoria) {
        this.idHistoria = idHistoria;
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


    public Cita getCita() {
        return cita;
    }


    public void setCita(Cita cita) {
        this.cita = cita;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public String getMotivoConsulta() {
        return motivoConsulta;
    }


    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }


    public String getDiagnostico() {
        return diagnostico;
    }


    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }


    public String getTratamiento() {
        return tratamiento;
    }


    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }


    public String getObservaciones() {
        return observaciones;
    }


    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}