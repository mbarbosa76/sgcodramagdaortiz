package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;



/*
============================================================
HistoriaClinicaRepository.java

Sistema de Gestión de Citas Odontológicas

Repositorio encargado del acceso a datos
de Historia Clínica.

Incluye consultas específicas para
los indicadores del Dashboard.

============================================================
*/


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;




@Repository
public interface HistoriaClinicaRepository
        extends JpaRepository<HistoriaClinica, Long> {



    /*
    ========================================================
    CONTAR HISTORIAS DE UN PROFESIONAL

    Permite saber cuántas historias clínicas
    ha realizado un profesional específico.

    ========================================================
    */


    long countByProfesionalIdProfesional(
            Long idProfesional
    );





    /*
    ========================================================
    CONTAR PACIENTES ÚNICOS ATENDIDOS

    Evita contar repetidamente el mismo paciente.

    Ejemplo:

    Historia 1 → Paciente 5
    Historia 2 → Paciente 5
    Historia 3 → Paciente 8


    Resultado:

    Pacientes atendidos = 2

    ========================================================
    */


    @Query(
        "SELECT COUNT(DISTINCT h.paciente.idPaciente) " +
        "FROM HistoriaClinica h " +
        "WHERE h.profesional.idProfesional = :idProfesional"
    )
    long contarPacientesAtendidosPorProfesional(
            @Param("idProfesional")
            Long idProfesional
    );


}