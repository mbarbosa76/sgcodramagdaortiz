/*
============================================================
FacturacionMensualResponse.java

Sistema de Gestión de Citas Odontológicas

DTO utilizado para representar la facturación
agrupada por mes.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



public class FacturacionMensualResponse {



    private String mes;


    private Integer total;





    public FacturacionMensualResponse(){

    }





    public FacturacionMensualResponse(
            String mes,
            Integer total
    ){

        this.mes = mes;

        this.total = total;

    }





    public String getMes(){

        return mes;

    }





    public void setMes(
            String mes
    ){

        this.mes = mes;

    }





    public Integer getTotal(){

        return total;

    }





    public void setTotal(
            Integer total
    ){

        this.total = total;

    }


}