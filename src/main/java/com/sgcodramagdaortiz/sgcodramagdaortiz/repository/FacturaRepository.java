package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;


/**
 * ============================================================
 * FACTURAREPOSITORY.JAVA
 * ============================================================
 *
 * Repositorio de acceso a datos de Factura.
 *
 * ============================================================
 */

@Repository
public interface FacturaRepository
        extends JpaRepository<Factura, Long> {


    /**
     * Busca la última factura registrada
     * por número de factura.
     *
     * Ejemplo:
     *
     * FAC-0002
     *
     * será la última antes de crear:
     *
     * FAC-0003
     */
    Optional<Factura> findTopByOrderByNumeroFacturaDesc();


}