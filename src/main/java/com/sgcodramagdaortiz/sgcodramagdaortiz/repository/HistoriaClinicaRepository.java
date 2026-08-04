package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;


/**
 * ============================================================
 * HISTORIACLINICAREPOSITORY.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Repositorio encargado del acceso a datos de la entidad
 * HistoriaClinica.
 *
 * Hereda las operaciones CRUD básicas de JpaRepository:
 *
 * - findAll()
 * - findById()
 * - save()
 * - deleteById()
 *
 * ============================================================
 */

@Repository
public interface HistoriaClinicaRepository
        extends JpaRepository<HistoriaClinica, Long> {


}