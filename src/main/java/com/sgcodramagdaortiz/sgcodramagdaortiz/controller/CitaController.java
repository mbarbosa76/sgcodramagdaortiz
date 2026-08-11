/*
============================================================
CitaController.java

Sistema de Gestión de Citas Odontológicas

Controlador REST encargado de gestionar las citas.

Reglas:

Admin:
- Ve todas las citas.
- Puede crear.
- Puede editar.
- Puede eliminar.


Recepcionista:
- Ve todas las citas.
- Puede crear.
- Puede editar.
- Puede eliminar.


Profesional:
- Ve sus propias citas.
- Puede crear citas de remisión.
- Solo modifica sus propias citas.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;



import java.util.List;

import java.util.Optional;



import org.springframework.http.ResponseEntity;

import org.springframework.lang.NonNull;

import org.springframework.security.core.Authentication;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

import com.sgcodramagdaortiz.sgcodramagdaortiz.service.CitaService;



import jakarta.validation.Valid;







@RestController

@RequestMapping("/api/citas")

@CrossOrigin(origins = "http://localhost:5173")

@Validated

public class CitaController {






    private final CitaService citaService;







    public CitaController(

            CitaService citaService

    ){


        this.citaService = citaService;


    }









    /*
    ========================================================
    LISTAR CITAS SEGÚN ROL

    GET:

    /api/citas

    ========================================================
    */


    @GetMapping

    public List<Cita> listarCitas(

            Authentication authentication

    ){



        return citaService
                .listarCitas(

                        authentication

                );


    }









    /*
    ========================================================
    BUSCAR CITA POR ID

    GET:

    /api/citas/{id}

    ========================================================
    */


    @GetMapping("/{idCita}")

    public ResponseEntity<Cita>
    buscarCitaPorId(

            @PathVariable
            @NonNull
            Long idCita

    ){



        Optional<Cita> cita =

                citaService
                .buscarCitaPorId(

                        idCita

                );





        return cita

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
    CREAR CITA

    POST:

    /api/citas


    Permite:

    Admin.
    Recepcionista.
    Profesional (remisiones).

    ========================================================
    */


    @PostMapping

    public ResponseEntity<?> guardarCita(

            @Valid
            @RequestBody
            Cita cita,


            Authentication authentication

    ){



        try {



            Cita nuevaCita =

                    citaService
                    .guardarCita(

                            cita,

                            authentication

                    );





            return ResponseEntity.ok(

                    nuevaCita

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
    ACTUALIZAR CITA

    PUT:

    /api/citas/{id}


    Validación:

    Profesional solamente puede
    modificar sus propias citas.

    ========================================================
    */


    @PutMapping("/{idCita}")

    public ResponseEntity<?> actualizarCita(

            @PathVariable
            @NonNull
            Long idCita,


            @Valid
            @RequestBody
            Cita cita,


            Authentication authentication

    ){



        Optional<Cita> existente =

                citaService
                .buscarCitaPorId(

                        idCita

                );





        if(existente.isEmpty()){


            return ResponseEntity

                    .notFound()

                    .build();


        }






        cita.setIdCita(

                idCita

        );






        try {



            return ResponseEntity.ok(

                    citaService
                    .guardarCita(

                            cita,

                            authentication

                    )

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
    ELIMINAR CITA

    DELETE:

    /api/citas/{id}


    Profesional:
    Solo sus propias citas.

    ========================================================
    */


    @DeleteMapping("/{idCita}")

    public ResponseEntity<?> eliminarCita(

            @PathVariable
            @NonNull
            Long idCita,


            Authentication authentication

    ){



        try {



            boolean eliminado =

                    citaService
                    .eliminarCita(

                            idCita,

                            authentication

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

        catch(RuntimeException e){



            return ResponseEntity

                    .status(403)

                    .body(

                            e.getMessage()

                    );


        }


    }





}