/*
============================================================
CitasEstadoResponse.java

Sistema de Gestión de Citas Odontológicas

DTO utilizado para representar la cantidad
de citas agrupadas por estado.

Ejemplo:

PROGRAMADA: 10
ASISTIDA: 15

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



public class CitasEstadoResponse {



    private String estado;


    private Long cantidad;




    public CitasEstadoResponse(){

    }




    public CitasEstadoResponse(
            String estado,
            Long cantidad
    ){

        this.estado = estado;

        this.cantidad = cantidad;

    }




    public String getEstado(){

        return estado;

    }




    public void setEstado(
            String estado
    ){

        this.estado = estado;

    }




    public Long getCantidad(){

        return cantidad;

    }




    public void setCantidad(
            Long cantidad
    ){

        this.cantidad = cantidad;

    }


}