package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;


/**
 * ============================================================
 * DETALLEFACTURAREPOSITORY.JAVA
 * ============================================================
 */

@Repository
public interface DetalleFacturaRepository
        extends JpaRepository<DetalleFactura, Long> {


    /**
     * Busca todos los detalles pertenecientes
     * a una factura.
     */
    List<DetalleFactura> findByFactura(Factura factura);


}