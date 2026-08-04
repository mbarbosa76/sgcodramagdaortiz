package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.HistoriaClinicaService;

import jakarta.validation.Valid;


/**
 * ============================================================
 * HISTORIACLINICACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Controlador REST encargado de gestionar las historias
 * clínicas odontológicas.
 *
 * Endpoints:
 *
 * GET     /api/historias
 * GET     /api/historias/{id}
 * POST    /api/historias
 * PUT     /api/historias/{id}
 * DELETE  /api/historias/{id}
 *
 * ============================================================
 */


@RestController
@RequestMapping("/api/historias")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class HistoriaClinicaController {



    private final HistoriaClinicaService historiaService;



    public HistoriaClinicaController(
            HistoriaClinicaService historiaService) {

        this.historiaService = historiaService;

    }



    /**
     * Listar todas las historias clínicas.
     */
    @GetMapping
    public List<HistoriaClinica> listarHistorias() {

        return historiaService.listarHistorias();

    }



    /**
     * Buscar historia por ID.
     */
    @GetMapping("/{idHistoria}")
    public ResponseEntity<HistoriaClinica> buscarHistoria(
            @PathVariable @NonNull Long idHistoria) {


        Optional<HistoriaClinica> historia =
                historiaService.buscarHistoriaPorId(idHistoria);



        return historia
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );

    }




    /**
     * Crear historia clínica.
     */
    @PostMapping
    public ResponseEntity<?> guardarHistoria(
            @Valid @RequestBody HistoriaClinica historiaClinica) {


        try {


            HistoriaClinica guardada =
                    historiaService.guardarHistoria(
                            historiaClinica
                    );


            return ResponseEntity.ok(guardada);



        } catch (RuntimeException e) {


            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }




    /**
     * Actualizar historia clínica.
     */
    @PutMapping("/{idHistoria}")
    public ResponseEntity<?> actualizarHistoria(
            @PathVariable @NonNull Long idHistoria,
            @Valid @RequestBody HistoriaClinica historiaClinica) {



        Optional<HistoriaClinica> actualizada =
                historiaService.actualizarHistoria(
                        idHistoria,
                        historiaClinica
                );



        return actualizada
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );

    }




    /**
     * Eliminar historia clínica.
     */
    @DeleteMapping("/{idHistoria}")
    public ResponseEntity<Void> eliminarHistoria(
            @PathVariable @NonNull Long idHistoria) {



        boolean eliminado =
                historiaService.eliminarHistoria(idHistoria);



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