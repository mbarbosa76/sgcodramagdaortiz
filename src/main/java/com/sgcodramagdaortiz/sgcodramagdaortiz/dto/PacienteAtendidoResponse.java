/*
============================================================
PacienteAtendidoResponse.java

Sistema de Gestión de Citas Odontológicas

DTO utilizado para transportar la información
de pacientes que tienen una cita atendida.

Regla de negocio:

Solo pacientes con citas en estado ASISTIDA
pueden aparecer en Historia Clínica.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;




public class PacienteAtendidoResponse {




    /*
    ========================================================
    IDENTIFICADOR DEL PACIENTE

    ========================================================
    */


    private Long idPaciente;






    /*
    ========================================================
    NOMBRE DEL PACIENTE

    ========================================================
    */


    private String nombre;






    /*
    ========================================================
    APELLIDO DEL PACIENTE

    ========================================================
    */


    private String apellido;









    /*
    ========================================================
    CONSTRUCTOR VACÍO

    Requerido para crear objetos DTO.

    ========================================================
    */


    public PacienteAtendidoResponse(){

    }









    /*
    ========================================================
    CONSTRUCTOR COMPLETO

    Facilita crear respuestas desde el servicio.

    ========================================================
    */


    public PacienteAtendidoResponse(

            Long idPaciente,

            String nombre,

            String apellido

    ){


        this.idPaciente = idPaciente;

        this.nombre = nombre;

        this.apellido = apellido;


    }









    /*
    ========================================================
    GETTERS Y SETTERS

    ========================================================
    */


    public Long getIdPaciente() {

        return idPaciente;

    }



    public void setIdPaciente(
            Long idPaciente
    ) {

        this.idPaciente = idPaciente;

    }







    public String getNombre() {

        return nombre;

    }



    public void setNombre(
            String nombre
    ) {

        this.nombre = nombre;

    }







    public String getApellido() {

        return apellido;

    }



    public void setApellido(
            String apellido
    ) {

        this.apellido = apellido;

    }



}