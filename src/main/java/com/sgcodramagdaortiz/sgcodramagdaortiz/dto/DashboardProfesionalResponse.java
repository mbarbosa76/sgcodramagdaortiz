/*
============================================================
DashboardProfesionalResponse.java

Sistema de Gestión de Citas Odontológicas

DTO encargado de transportar indicadores
del profesional autenticado.

Solamente muestra información propia.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



public class DashboardProfesionalResponse {



    /*
    ========================================================
    INDICADORES DEL PROFESIONAL
    ========================================================
    */


    private Long misCitas;


    private Long misPacientes;


    private Long misHistorias;





    public DashboardProfesionalResponse(){

    }





    public Long getMisCitas() {

        return misCitas;

    }


    public void setMisCitas(Long misCitas) {

        this.misCitas = misCitas;

    }





    public Long getMisPacientes() {

        return misPacientes;

    }


    public void setMisPacientes(Long misPacientes) {

        this.misPacientes = misPacientes;

    }





    public Long getMisHistorias() {

        return misHistorias;

    }


    public void setMisHistorias(Long misHistorias) {

        this.misHistorias = misHistorias;

    }


}