/*
============================================================
DashboardAdminResponse.java

Sistema de Gestión de Citas Odontológicas

DTO utilizado para transportar los indicadores
generales visibles para el administrador.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



public class DashboardAdminResponse {



    private Long usuarios;


    private Long pacientes;


    private Long profesionales;


    private Long citas;


    private Long historias;


    private Integer facturacion;




    /*
    ========================================================
    CONSTRUCTOR VACÍO

    Requerido para creación del objeto.

    ========================================================
    */

    public DashboardAdminResponse(){

    }






    public Long getUsuarios() {

        return usuarios;

    }


    public void setUsuarios(Long usuarios) {

        this.usuarios = usuarios;

    }





    public Long getPacientes() {

        return pacientes;

    }


    public void setPacientes(Long pacientes) {

        this.pacientes = pacientes;

    }





    public Long getProfesionales() {

        return profesionales;

    }


    public void setProfesionales(Long profesionales) {

        this.profesionales = profesionales;

    }





    public Long getCitas() {

        return citas;

    }


    public void setCitas(Long citas) {

        this.citas = citas;

    }





    public Long getHistorias() {

        return historias;

    }


    public void setHistorias(Long historias) {

        this.historias = historias;

    }





    public Integer getFacturacion() {

        return facturacion;

    }


    public void setFacturacion(Integer facturacion) {

        this.facturacion = facturacion;

    }



}