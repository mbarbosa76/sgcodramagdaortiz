package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.util.List;
import java.util.Optional;


import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.ActualizarFacturaDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.FacturaRequestDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;


/**
 * ============================================================
 * FACTURASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Define las operaciones disponibles para el módulo Facturación.
 *
 * ============================================================
 */
public interface FacturaService {



    /**
     * Lista todas las facturas registradas.
     */
    List<Factura> listarFacturas();




    /**
     * Busca una factura específica mediante su identificador.
     */
    Optional<Factura> buscarFacturaPorId(
            Long idFactura
    );




    /**
     * Guarda una factura tradicional.
     */
    Factura guardarFactura(
            Factura factura
    );




    /**
     * Crea una factura completa:
     *
     * - Factura.
     * - Detalles.
     * - Total automático.
     */
    Factura crearFacturaCompleta(
            FacturaRequestDTO facturaRequest
    );




    /**
     * ============================================================
     * ACTUALIZAR FACTURA
     * ============================================================
     *
     * Recibe un DTO porque solamente se deben modificar
     * campos administrativos de la factura.
     *
     * No se modifican:
     *
     * - Paciente.
     * - Cita.
     * - Servicios asociados.
     *
     * Esto evita problemas de validación y mantiene
     * la integridad de la información histórica.
     */
    Optional<Factura> actualizarFactura(
            Long idFactura,
            ActualizarFacturaDTO facturaRequest
    );




    /**
     * Elimina una factura mediante su identificador.
     */
    boolean eliminarFactura(
            Long idFactura
    );


}