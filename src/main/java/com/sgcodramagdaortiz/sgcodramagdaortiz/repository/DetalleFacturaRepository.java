package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;


/**
 * ============================================================
 * DETALLEFACTURAREPOSITORY.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Repositorio encargado del acceso a datos de los detalles
 * de factura.
 *
 * ============================================================
 */

@Repository
public interface DetalleFacturaRepository
        extends JpaRepository<DetalleFactura, Long> {


}