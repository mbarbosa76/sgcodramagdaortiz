package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;


import java.util.List;


/**
 * ============================================================
 * FACTURAREQUESTDTO.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * DTO utilizado para recibir la información necesaria
 * para crear una factura completa.
 *
 * La factura se genera a partir de:
 *
 * - Paciente.
 * - Cita.
 * - Servicios seleccionados.
 *
 * El total NO viene desde el frontend.
 * El backend lo calcula automáticamente.
 *
 * Ejemplo:
 *
 * {
 *   "idPaciente":1,
 *   "idCita":24,
 *   "estadoPago":"PENDIENTE",
 *   "servicios":[1,2]
 * }
 *
 * ============================================================
 */


public class FacturaRequestDTO {



    /**
     * Paciente asociado a la factura.
     */
    private Long idPaciente;



    /**
     * Cita relacionada con la factura.
     */
    private Long idCita;



    /**
     * Estado inicial de pago.
     *
     * Valores:
     *
     * PENDIENTE
     * PAGADA
     * CANCELADA
     */
    private String estadoPago;



    /**
     * Lista de servicios seleccionados
     * para generar los detalles.
     *
     * Ejemplo:
     *
     * [1,2,3]
     */
    private List<Long> servicios;




    public FacturaRequestDTO() {

    }




    public Long getIdPaciente() {
        return idPaciente;
    }


    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }




    public Long getIdCita() {
        return idCita;
    }


    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }




    public String getEstadoPago() {
        return estadoPago;
    }


    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }




    public List<Long> getServicios() {
        return servicios;
    }


    public void setServicios(List<Long> servicios) {
        this.servicios = servicios;
    }


}