/*
============================================================
HistoriaClinicaService.java

Sistema de Gestión de Citas Odontológicas

Interfaz que define las operaciones
del módulo Historia Clínica.

Responsabilidades:

- Consultar historias clínicas.
- Registrar historias clínicas.
- Actualizar historias clínicas.
- Eliminar historias clínicas.
- Obtener pacientes con atención realizada.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.service;



import java.util.List;

import java.util.Optional;



import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.PacienteAtendidoResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;





public interface HistoriaClinicaService {






    /*
    ========================================================
    LISTAR HISTORIAS CLÍNICAS

    ========================================================
    */


    List<HistoriaClinica> listarHistorias();








    /*
    ========================================================
    BUSCAR HISTORIA POR ID

    ========================================================
    */


    Optional<HistoriaClinica> buscarHistoriaPorId(

            Long idHistoria

    );








    /*
    ========================================================
    CREAR HISTORIA CLÍNICA

    ========================================================
    */


    HistoriaClinica guardarHistoria(

            HistoriaClinica historiaClinica

    );








    /*
    ========================================================
    ACTUALIZAR HISTORIA CLÍNICA

    ========================================================
    */


    Optional<HistoriaClinica> actualizarHistoria(

            Long idHistoria,

            HistoriaClinica historiaClinica

    );








    /*
    ========================================================
    ELIMINAR HISTORIA CLÍNICA

    ========================================================
    */


    boolean eliminarHistoria(

            Long idHistoria

    );








    /*
    ========================================================
    PACIENTES ATENDIDOS

    Devuelve únicamente pacientes que tienen
    citas con estado ASISTIDA.

    Regla de negocio:

    Solo pacientes atendidos pueden generar
    una Historia Clínica.

    ========================================================
    */


    List<PacienteAtendidoResponse>
    listarPacientesAtendidos();



}