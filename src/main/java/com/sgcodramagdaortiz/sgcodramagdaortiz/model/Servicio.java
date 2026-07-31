package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidad que representa un servicio odontológico.
 */
@Entity
@Table(name = "servicio")
public class Servicio {

    /**
     * Identificador único del servicio.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    /**
     * Código único del servicio.
     */
    @NotBlank(message = "El código del servicio es obligatorio")
    @Size(max = 20)
    @Column(name = "cod_servicio", nullable = false, unique = true)
    private String codServicio;

    /**
     * Nombre del servicio odontológico.
     */
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 150)
    @Column(name = "nombre_servicio", nullable = false)
    private String nombreServicio;

    /**
     * Descripción del servicio.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Duración estimada del servicio en minutos.
     */
    @NotNull(message = "La duración es obligatoria")
    @Column(name = "duracion_min", nullable = false)
    private Integer duracionMin;

    /**
     * Precio del servicio.
     */
    @NotNull(message = "El precio es obligatorio")
    @Column(name = "precio", nullable = false)
    private Integer precio;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Servicio() {
    }

    // ====================== GETTERS Y SETTERS ======================

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getCodServicio() {
        return codServicio;
    }

    public void setCodServicio(String codServicio) {
        this.codServicio = codServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(Integer duracionMin) {
        this.duracionMin = duracionMin;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}