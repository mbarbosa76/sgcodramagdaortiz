package com.sgcodramagdaortiz.sgcodramagdaortiz.model;

// ============================================================
// Importación de clases necesarias
// ============================================================

import java.time.LocalDate;

// ============================================================
// Importación de anotaciones JPA
// ============================================================

import jakarta.persistence.*;

// ============================================================
// Importación de Bean Validation
// ============================================================

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * ============================================================
 * ENTIDAD SERVICIO
 * ============================================================
 *
 * Representa los servicios odontológicos que ofrece el
 * Consultorio Odontológico Dra. Magda Ortiz.
 *
 * Esta entidad almacena la información de cada procedimiento
 * que puede ser programado mediante una cita odontológica.
 *
 * Será utilizada posteriormente por los módulos:
 *
 * • Gestión de Citas
 * • Historia Clínica
 * • Facturación
 *
 * ============================================================
 */

@Entity
@Table(name = "servicio")
public class Servicio {

    /**
     * Identificador único del servicio.
     * Llave primaria de la tabla.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;

    /**
     * Código interno del servicio.
     * Ejemplo:
     * SERV-001
     * SERV-002
     */
    @NotBlank(message = "El código del servicio es obligatorio")
    @Size(max = 20)
    @Column(name = "cod_servicio", nullable = false, unique = true)
    private String codServicio;

    /**
     * Nombre del servicio odontológico.
     */
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre_servicio", nullable = false)
    private String nombreServicio;

    /**
     * Descripción detallada del servicio.
     */
    @NotBlank(message = "La descripción del servicio es obligatoria")
    @Size(max = 500)
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    /**
     * Categoría del servicio.
     *
     * Ejemplo:
     * Preventivo
     * Restaurativo
     * Estético
     * Cirugía
     */
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 50)
    @Column(name = "categoria", nullable = false)
    private String categoria;

    /**
     * Duración estimada del procedimiento en minutos.
     */
    @NotNull(message = "La duración del servicio es obligatoria")
    @Positive(message = "La duración debe ser mayor que cero")
    @Column(name = "duracion_min", nullable = false)
    private Integer duracionMin;

    /**
     * Precio del servicio.
     *
     * Se almacena en pesos colombianos.
     */
    @NotNull(message = "El precio del servicio es obligatorio")
    @Positive(message = "El precio debe ser mayor que cero")
    @Column(name = "precio", nullable = false)
    private Integer precio;

    /**
     * Estado del servicio.
     *
     * TRUE = Disponible
     * FALSE = Inactivo
     */
    @Column(name = "activo")
    private Boolean activo = true;

    /**
     * Fecha en la que fue creado el registro.
     */
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    /**
     * Fecha de la última modificación.
     */
    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Servicio() {
    }

    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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