/*
============================================================
DashboardRecepcionResponse.java

Sistema de Gestión de Citas Odontológicas

DTO encargado de transportar indicadores
operativos para recepción.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



public class DashboardRecepcionResponse {



    private Long pacientes;


    private Long citasPendientes;


    private Long facturasPendientes;





    public DashboardRecepcionResponse(){

    }





    public Long getPacientes() {

        return pacientes;

    }


    public void setPacientes(Long pacientes) {

        this.pacientes = pacientes;

    }





    public Long getCitasPendientes() {

        return citasPendientes;

    }


    public void setCitasPendientes(Long citasPendientes) {

        this.citasPendientes = citasPendientes;

    }





    public Long getFacturasPendientes() {

        return facturasPendientes;

    }


    public void setFacturasPendientes(Long facturasPendientes) {

        this.facturasPendientes = facturasPendientes;

    }


}