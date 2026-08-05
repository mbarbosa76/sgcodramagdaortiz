package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.util.List;
import java.util.Optional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;


/**
 * ============================================================
 * DETALLEFACTURASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Define las operaciones del módulo Detalle Factura.
 *
 * Responsabilidades:
 *
 * - Listar detalles.
 * - Buscar detalles.
 * - Crear detalles.
 * - Actualizar detalles.
 * - Eliminar detalles.
 *
 * ============================================================
 */

public interface DetalleFacturaService {


    List<DetalleFactura> listarDetalles();



    Optional<DetalleFactura> buscarDetallePorId(
            Long idDetalle
    );



    DetalleFactura guardarDetalle(
            DetalleFactura detalleFactura
    );

List<DetalleFactura> listarPorFactura(
        Long idFactura
);

    Optional<DetalleFactura> actualizarDetalle(
            Long idDetalle,
            DetalleFactura detalleFactura
    );



    boolean eliminarDetalle(
            Long idDetalle
    );


}