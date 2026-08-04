package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.util.List;
import java.util.Optional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;


/**
 * ============================================================
 * HISTORIACLINICASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Interfaz que define las operaciones del módulo
 * Historia Clínica.
 *
 * ============================================================
 */

public interface HistoriaClinicaService {


    List<HistoriaClinica> listarHistorias();


    Optional<HistoriaClinica> buscarHistoriaPorId(
            Long idHistoria
    );


    HistoriaClinica guardarHistoria(
            HistoriaClinica historiaClinica
    );


    Optional<HistoriaClinica> actualizarHistoria(
            Long idHistoria,
            HistoriaClinica historiaClinica
    );


    boolean eliminarHistoria(
            Long idHistoria
    );

}