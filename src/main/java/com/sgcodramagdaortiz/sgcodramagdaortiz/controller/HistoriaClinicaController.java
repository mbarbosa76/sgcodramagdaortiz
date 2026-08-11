/*
============================================================
HistoriaClinicaController.java

Sistema de Gestión de Citas Odontológicas

Controlador REST encargado de gestionar
las historias clínicas odontológicas.

Endpoints:

GET     /api/historias

GET     /api/historias/pacientes-atendidos

GET     /api/historias/{id}

POST    /api/historias

PUT     /api/historias/{id}

DELETE  /api/historias/{id}

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;



import java.util.List;

import java.util.Optional;



import org.springframework.http.ResponseEntity;

import org.springframework.lang.NonNull;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;



import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.PacienteAtendidoResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;

import com.sgcodramagdaortiz.sgcodramagdaortiz.service.HistoriaClinicaService;



import jakarta.validation.Valid;







@RestController

@RequestMapping("/api/historias")

@CrossOrigin(origins = "http://localhost:5173")

@Validated

public class HistoriaClinicaController {







    /*
    ========================================================
    SERVICIO DE HISTORIA CLÍNICA

    ========================================================
    */


    private final HistoriaClinicaService historiaService;







    public HistoriaClinicaController(

            HistoriaClinicaService historiaService

    ){


        this.historiaService = historiaService;


    }









    /*
    ========================================================
    LISTAR HISTORIAS CLÍNICAS

    GET:

    /api/historias

    ========================================================
    */


    @GetMapping

    public List<HistoriaClinica> listarHistorias(){



        return historiaService
                .listarHistorias();


    }









    /*
    ========================================================
    LISTAR PACIENTES ATENDIDOS

    Devuelve únicamente pacientes
    con citas en estado ASISTIDA.

    Utilizado por:

    Formulario Historia Clínica.

    GET:

    /api/historias/pacientes-atendidos

    ========================================================
    */


    @GetMapping("/pacientes-atendidos")

    public ResponseEntity<List<PacienteAtendidoResponse>>
    listarPacientesAtendidos(){



        return ResponseEntity.ok(

                historiaService
                .listarPacientesAtendidos()

        );


    }









    /*
    ========================================================
    BUSCAR HISTORIA POR ID

    GET:

    /api/historias/{idHistoria}

    ========================================================
    */


    @GetMapping("/{idHistoria}")

    public ResponseEntity<HistoriaClinica>
    buscarHistoria(

            @PathVariable
            @NonNull
            Long idHistoria

    ){



        Optional<HistoriaClinica> historia =

                historiaService
                .buscarHistoriaPorId(

                        idHistoria

                );





        return historia

                .map(ResponseEntity::ok)

                .orElseGet(

                    () ->
                    ResponseEntity
                    .notFound()
                    .build()

                );


    }









    /*
    ========================================================
    CREAR HISTORIA CLÍNICA

    POST:

    /api/historias

    ========================================================
    */


    @PostMapping

    public ResponseEntity<?> guardarHistoria(

            @Valid
            @RequestBody
            HistoriaClinica historiaClinica

    ){



        try {



            HistoriaClinica guardada =

                    historiaService
                    .guardarHistoria(

                            historiaClinica

                    );





            return ResponseEntity.ok(

                    guardada

            );



        }

        catch(RuntimeException e){



            return ResponseEntity

                    .badRequest()

                    .body(

                            e.getMessage()

                    );


        }


    }









    /*
    ========================================================
    ACTUALIZAR HISTORIA CLÍNICA

    PUT:

    /api/historias/{idHistoria}

    ========================================================
    */


    @PutMapping("/{idHistoria}")

    public ResponseEntity<?> actualizarHistoria(

            @PathVariable
            @NonNull
            Long idHistoria,


            @Valid
            @RequestBody
            HistoriaClinica historiaClinica

    ){



        Optional<HistoriaClinica> actualizada =

                historiaService
                .actualizarHistoria(

                        idHistoria,

                        historiaClinica

                );





        return actualizada

                .map(ResponseEntity::ok)

                .orElseGet(

                    () ->
                    ResponseEntity
                    .notFound()
                    .build()

                );


    }









    /*
    ========================================================
    ELIMINAR HISTORIA CLÍNICA

    DELETE:

    /api/historias/{idHistoria}

    ========================================================
    */


    @DeleteMapping("/{idHistoria}")

    public ResponseEntity<Void>
    eliminarHistoria(

            @PathVariable
            @NonNull
            Long idHistoria

    ){



        boolean eliminado =

                historiaService
                .eliminarHistoria(

                        idHistoria

                );





        if(eliminado){



            return ResponseEntity

                    .noContent()

                    .build();


        }





        return ResponseEntity

                .notFound()

                .build();


    }





}