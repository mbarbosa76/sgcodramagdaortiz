/*
============================================================
CitaRepository.java

Sistema de Gestión de Citas Odontológicas

Repositorio encargado del acceso a datos
de la entidad Cita.

Funciones:

- CRUD de citas.
- Conteo por profesional.
- Consultas Dashboard.
- Filtrado de citas por profesional autenticado.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;



import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;





@Repository
public interface CitaRepository
        extends JpaRepository<Cita, Long> {







    /*
    ========================================================
    CITAS DE UN PROFESIONAL

    Utilizado cuando un usuario con rol Profesional
    consulta el módulo Citas.

    Ejemplo:

    Magda Ortiz

    devuelve únicamente sus citas.

    ========================================================
    */


    List<Cita> findByProfesionalIdProfesional(

            Long idProfesional

    );









    /*
    ========================================================
    CONTAR CITAS DE UN PROFESIONAL

    Dashboard Profesional.

    ========================================================
    */


    long countByProfesionalIdProfesional(

            Long idProfesional

    );









    /*
    ========================================================
    CONTAR CITAS POR ESTADO

    ========================================================
    */


    long countByEstado(

            String estado

    );









    /*
    ========================================================
    DISTRIBUCIÓN GENERAL DE CITAS

    Dashboard Administrador.

    ========================================================
    */


    @Query("""
            SELECT
                c.estado,
                COUNT(c.idCita)
            FROM Cita c
            GROUP BY c.estado
            ORDER BY
                CASE c.estado
                    WHEN 'PROGRAMADA' THEN 1
                    WHEN 'CONFIRMADA' THEN 2
                    WHEN 'ASISTIDA' THEN 3
                    WHEN 'NO ASISTIDA' THEN 4
                    WHEN 'CANCELADA' THEN 5
                    ELSE 6
                END
            """)
    List<Object[]> contarCitasPorEstado();









    /*
    ========================================================
    DISTRIBUCIÓN DE CITAS POR PROFESIONAL

    Dashboard Profesional.

    ========================================================
    */


    @Query("""
            SELECT
                c.estado,
                COUNT(c.idCita)
            FROM Cita c
            WHERE c.profesional.idProfesional = :idProfesional
            GROUP BY c.estado
            ORDER BY
                CASE c.estado
                    WHEN 'PROGRAMADA' THEN 1
                    WHEN 'CONFIRMADA' THEN 2
                    WHEN 'ASISTIDA' THEN 3
                    WHEN 'NO ASISTIDA' THEN 4
                    WHEN 'CANCELADA' THEN 5
                    ELSE 6
                END
            """)
    List<Object[]> contarCitasPorEstadoProfesional(

            @Param("idProfesional")
            Long idProfesional

    );









    /*
    ========================================================
    CITAS ASISTIDAS

    Historia Clínica.

    ========================================================
    */


    List<Cita> findByEstado(

            String estado

    );





}