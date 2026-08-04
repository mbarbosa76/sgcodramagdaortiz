package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.util.List;
import java.util.Optional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;


/**
 * ============================================================
 * FACTURASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Define las operaciones del módulo Facturación.
 *
 * ============================================================
 */

public interface FacturaService {


    List<Factura> listarFacturas();


    Optional<Factura> buscarFacturaPorId(
            Long idFactura
    );


    Factura guardarFactura(
            Factura factura
    );


    Optional<Factura> actualizarFactura(
            Long idFactura,
            Factura factura
    );


    boolean eliminarFactura(
            Long idFactura
    );


}