/*
============================================================
DashboardController.java

Sistema de Gestión de Citas Odontológicas

Controlador REST encargado de exponer
la información utilizada por el Dashboard.

Endpoints:

Dashboard:

/api/dashboard/admin

/api/dashboard/profesional

/api/dashboard/recepcionista


Gráficos:

/api/dashboard/citas-estados

/api/dashboard/profesional/citas-estados

/api/dashboard/recepcionista/citas-estados

/api/dashboard/facturacion-mensual

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;



import java.util.List;



import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;



import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.CitasEstadoResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardAdminResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardProfesionalResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardRecepcionResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.FacturacionMensualResponse;



import com.sgcodramagdaortiz.sgcodramagdaortiz.service.DashboardService;





@RestController

@RequestMapping("/api/dashboard")

@CrossOrigin(origins = "http://localhost:5173")

public class DashboardController {





    /*
    ========================================================
    SERVICIO DASHBOARD

    Contiene la lógica para construir
    indicadores y gráficos.

    ========================================================
    */


    private final DashboardService dashboardService;







    public DashboardController(

            DashboardService dashboardService

    ){


        this.dashboardService =

                dashboardService;


    }









    /*
    ========================================================
    DASHBOARD ADMINISTRADOR

    GET:

    /api/dashboard/admin

    ========================================================
    */


    @GetMapping("/admin")

    public ResponseEntity<DashboardAdminResponse>
    dashboardAdmin(){



        return ResponseEntity.ok(

                dashboardService
                .obtenerDashboardAdmin()

        );


    }









    /*
    ========================================================
    DASHBOARD PROFESIONAL

    GET:

    /api/dashboard/profesional

    ========================================================
    */


    @GetMapping("/profesional")

    public ResponseEntity<DashboardProfesionalResponse>
    dashboardProfesional(

            Authentication authentication

    ){



        return ResponseEntity.ok(

                dashboardService
                .obtenerDashboardProfesional(

                        authentication.getName()

                )

        );


    }









    /*
    ========================================================
    DASHBOARD RECEPCIONISTA

    GET:

    /api/dashboard/recepcionista

    ========================================================
    */


    @GetMapping("/recepcionista")

    public ResponseEntity<DashboardRecepcionResponse>
    dashboardRecepcionista(){



        return ResponseEntity.ok(

                dashboardService
                .obtenerDashboardRecepcionista()

        );


    }









    /*
    ========================================================
    GRÁFICO GENERAL DE CITAS

    Utilizado por:

    Administrador.

    GET:

    /api/dashboard/citas-estados

    ========================================================
    */


    @GetMapping("/citas-estados")

    public ResponseEntity<List<CitasEstadoResponse>>
    citasEstados(){



        return ResponseEntity.ok(

                dashboardService
                .obtenerCitasPorEstado()

        );


    }









    /*
    ========================================================
    GRÁFICO DE CITAS DEL PROFESIONAL

    Utilizado por:

    Profesional autenticado.

    GET:

    /api/dashboard/profesional/citas-estados

    ========================================================
    */


    @GetMapping("/profesional/citas-estados")

    public ResponseEntity<List<CitasEstadoResponse>>
    citasEstadosProfesional(

            Authentication authentication

    ){



        return ResponseEntity.ok(

                dashboardService
                .obtenerCitasEstadoProfesional(

                        authentication.getName()

                )

        );


    }









    /*
    ========================================================
    GRÁFICO DE CITAS RECEPCIÓN

    Utilizado por:

    Recepcionista.

    Muestra información operativa
    de agenda.

    GET:

    /api/dashboard/recepcionista/citas-estados

    ========================================================
    */


    @GetMapping("/recepcionista/citas-estados")

public ResponseEntity<List<CitasEstadoResponse>>
citasEstadosRecepcionista(

        Authentication authentication

){



    return ResponseEntity.ok(

            dashboardService
            .obtenerCitasEstadoRecepcionista()

    );


}





    /*
    ========================================================
    GRÁFICO FACTURACIÓN MENSUAL

    Utilizado por:

    Administrador.

    GET:

    /api/dashboard/facturacion-mensual

    ========================================================
    */


    @GetMapping("/facturacion-mensual")

    public ResponseEntity<List<FacturacionMensualResponse>>
    facturacionMensual(){



        return ResponseEntity.ok(

                dashboardService
                .obtenerFacturacionMensual()

        );


    }





}