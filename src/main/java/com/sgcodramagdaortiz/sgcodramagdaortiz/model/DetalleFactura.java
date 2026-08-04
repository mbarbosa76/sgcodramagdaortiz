package com.sgcodramagdaortiz.sgcodramagdaortiz.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * ============================================================
 * DETALLEFACTURA.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Representa cada servicio incluido dentro de una factura.
 *
 * Ejemplo:
 *
 * Factura:
 * FAC-001
 *
 * Detalle:
 * Limpieza dental - $80.000
 *
 * Relaciones:
 *
 * Factura 1 ---- N DetalleFactura
 *
 * Servicio 1 ---- N DetalleFactura
 *
 * ============================================================
 */

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura {


    /**
     * Identificador único.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;



    /**
     * Factura a la que pertenece el detalle.
     */
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(
                name = "id_factura",
                nullable = false
        )
        @NotNull(message = "La factura es obligatoria")
        private Factura factura;


    /**
     * Servicio realizado.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "id_servicio",
            nullable = false
    )
    @NotNull(message = "El servicio es obligatorio")
    private Servicio servicio;



    /**
     * Cantidad del servicio.
     */
    @Column(name = "cantidad")
    private Integer cantidad;



    /**
     * Subtotal del detalle.
     */
    @Column(name = "subtotal")
    private Integer subtotal;



    public DetalleFactura() {

    }



    public Long getIdDetalle() {
        return idDetalle;
    }


    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }


    public Factura getFactura() {
        return factura;
    }


    public void setFactura(Factura factura) {
        this.factura = factura;
    }


    public Servicio getServicio() {
        return servicio;
    }


    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }


    public Integer getCantidad() {
        return cantidad;
    }


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Integer getSubtotal() {
        return subtotal;
    }


    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

}