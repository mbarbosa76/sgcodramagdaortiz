package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

import java.time.LocalDate;

/**
 * ============================================================
 * ACTUALIZARFACTURADTO.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * DTO utilizado exclusivamente para actualizar una factura existente.
 *
 * IMPORTANTE:
 *
 * Una factura creada ya tiene relaciones obligatorias:
 *
 * - Paciente.
 * - Cita.
 * - Detalles de factura.
 *
 * Por esta razón no se utiliza directamente la entidad Factura
 * para actualizar datos simples, ya que Spring intentaría validar
 * nuevamente todos los campos obligatorios.
 *
 * Este DTO permite modificar únicamente los campos autorizados:
 *
 * - Fecha de factura.
 * - Estado de pago.
 * - Total.
 *
 * ============================================================
 */
public class ActualizarFacturaDTO {


    /**
     * Fecha registrada para la factura.
     */
    private LocalDate fecha;


    /**
     * Estado actual del pago:
     *
     * Ejemplos:
     *
     * - PENDIENTE
     * - PAGADA
     * - CANCELADA
     */
    private String estadoPago;


    /**
     * Valor total de la factura.
     */
    private Integer total;



    // ============================================================
    // GETTERS Y SETTERS
    // ============================================================


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

}