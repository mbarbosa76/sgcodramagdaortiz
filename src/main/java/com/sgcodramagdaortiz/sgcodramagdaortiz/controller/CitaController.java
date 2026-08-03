package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;


// ============================================================
// IMPORTACIONES
// ============================================================

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.CitaService;

import jakarta.validation.Valid;



/**
 * ============================================================
 * CITACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Controlador REST encargado de gestionar las citas.
 *
 * Endpoints:
 *
 * GET     /api/citas
 * GET     /api/citas/{id}
 * POST    /api/citas
 * PUT     /api/citas/{id}
 * DELETE  /api/citas/{id}
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class CitaController {



    private final CitaService citaService;



    public CitaController(
            CitaService citaService) {

        this.citaService = citaService;

    }



    /**
     * Lista todas las citas.
     */
    @GetMapping
    public List<Cita> listarCitas() {

        return citaService.listarCitas();

    }




    /**
     * Consulta una cita por ID.
     */
    @GetMapping("/{idCita}")
    public ResponseEntity<Cita> buscarCitaPorId(
            @PathVariable @NonNull Long idCita) {


        Optional<Cita> cita =
                citaService.buscarCitaPorId(idCita);


        return cita
                .map(ResponseEntity::ok)
                .orElseGet(
                    () -> ResponseEntity.notFound().build()
                );

    }





    /**
     * Crear una nueva cita.
     */
    @PostMapping
    public ResponseEntity<?> guardarCita(
            @Valid @RequestBody Cita cita) {


        try {


            Cita nuevaCita =
                    citaService.guardarCita(cita);


            return ResponseEntity.ok(nuevaCita);



        } catch (RuntimeException e) {


            return ResponseEntity
                    .badRequest()
                    .body(
                        e.getMessage()
                    );

        }

    }




    /**
     * Actualizar una cita existente.
     */
    @PutMapping("/{idCita}")
    public ResponseEntity<?> actualizarCita(
            @PathVariable @NonNull Long idCita,
            @Valid @RequestBody Cita cita) {


        Optional<Cita> existente =
                citaService.buscarCitaPorId(idCita);



        if (existente.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();

        }



        cita.setIdCita(idCita);



        try {


            return ResponseEntity.ok(
                    citaService.guardarCita(cita)
            );



        } catch (RuntimeException e) {


            return ResponseEntity
                    .badRequest()
                    .body(
                        e.getMessage()
                    );

        }

    }




    /**
     * Eliminar cita.
     */
    @DeleteMapping("/{idCita}")
    public ResponseEntity<Void> eliminarCita(
            @PathVariable @NonNull Long idCita) {



        boolean eliminado =
                citaService.eliminarCita(idCita);



        if (eliminado) {

            return ResponseEntity
                    .noContent()
                    .build();

        }


        return ResponseEntity
                .notFound()
                .build();

    }

}