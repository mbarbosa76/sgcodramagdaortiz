package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;


/**
 * ============================================================
 * FACTURAREPOSITORY.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Repositorio para acceder a los datos de Factura.
 *
 * Hereda operaciones CRUD de JpaRepository.
 *
 * ============================================================
 */

@Repository
public interface FacturaRepository
        extends JpaRepository<Factura, Long> {


}