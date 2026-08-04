package com.sgcodramagdaortiz.sgcodramagdaortiz.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;


/**
 * ============================================================
 * FACTURA.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Entidad que representa una factura generada
 * por la atención odontológica.
 *
 * Relaciones:
 *
 * Paciente 1 ---- N Facturas
 *
 * Cita 1 ---- 1 Factura
 *
 * Factura 1 ---- N DetallesFactura
 *
 * ============================================================
 */

@Entity
@Table(name = "factura")
public class Factura {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;



    /**
     * Número único de factura.
     */
    @Column(name = "numero_factura",
            unique = true,
            nullable = false)
    private String numeroFactura;



    /**
     * Fecha de generación.
     */
    @Column(name = "fecha")
    private LocalDate fecha;



    /**
     * Estado del pago:
     *
     * PENDIENTE
     * PAGADA
     * CANCELADA
     */
    @Column(name = "estado_pago")
    private String estadoPago;



    /**
     * Valor total de la factura.
     */
    @Column(name = "total")
    private Integer total;



    /**
     * Paciente asociado.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_paciente",
            nullable = false
    )
    @NotNull(message = "El paciente es obligatorio")
    private Paciente paciente;



    /**
     * Cita asociada.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_cita",
            nullable = false
    )
    @NotNull(message = "La cita es obligatoria")
    private Cita cita;



    /**
     * Detalles incluidos en la factura.
     */
        @JsonIgnore
        @OneToMany(
                mappedBy = "factura",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        private List<DetalleFactura> detalles;



    public Factura() {

    }



    public Long getIdFactura() {
        return idFactura;
    }


    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }


    public String getNumeroFactura() {
        return numeroFactura;
    }


    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }


    public LocalDate getFecha() {
        return fecha;
    }


    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public String getEstadoPago() {
        return estadoPago;
    }


    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }


    public Integer getTotal() {
        return total;
    }


    public void setTotal(Integer total) {
        this.total = total;
    }


    public Paciente getPaciente() {
        return paciente;
    }


    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    public Cita getCita() {
        return cita;
    }


    public void setCita(Cita cita) {
        this.cita = cita;
    }


    public List<DetalleFactura> getDetalles() {
        return detalles;
    }


    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

}