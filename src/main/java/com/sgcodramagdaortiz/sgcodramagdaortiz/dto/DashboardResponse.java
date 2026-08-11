/*
============================================================
DashboardResponse.java

Sistema de Gestión de Citas Odontológicas

DTO utilizado para enviar al frontend
la información del dashboard.

Contiene indicadores según el rol:

- Administrador.
- Profesional.
- Recepcionista.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;



/*
============================================================
CLASE DTO

No representa una tabla.

Solamente transporta información
entre backend y frontend.

============================================================
*/

public class DashboardResponse {



    /*
    ========================================================
    INDICADORES GENERALES
    ========================================================
    */

    private Long usuarios;

    private Long pacientes;

    private Long profesionales;

    private Long citas;

    private Long historias;



    /*
    ========================================================
    INFORMACIÓN FINANCIERA
    ========================================================
    */

    private Integer facturacion;



    /*
    ========================================================
    INDICADORES PROFESIONAL
    ========================================================
    */

    private Long misCitas;

    private Long misPacientes;

    private Long misHistorias;



    /*
    ========================================================
    INDICADORES RECEPCIÓN
    ========================================================
    */

    private Long citasPendientes;

    private Long facturasPendientes;



    /*
    ========================================================
    CONSTRUCTOR VACÍO
    ========================================================
    */

    public DashboardResponse() {

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