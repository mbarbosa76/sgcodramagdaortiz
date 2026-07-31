package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

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
 * Controlador REST para gestionar las Citas del sistema.
 */
@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class CitaController {

    private final CitaService citaService;

    /**
     * Constructor.
     */
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    /**
     * Lista todas las citas.
     *
     * GET /api/citas
     */
    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.listarCitas();
    }

    /**
     * Busca una cita por ID.
     *
     * GET /api/citas/1
     */
    @GetMapping("/{idCita}")
    public ResponseEntity<Cita> buscarCitaPorId(
            @PathVariable @NonNull Long idCita) {

        Optional<Cita> cita =
                citaService.buscarCitaPorId(idCita);

        return cita
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Registra una nueva cita.
     *
     * POST /api/citas
     */
    @PostMapping
    public Cita guardarCita(@Valid @RequestBody Cita cita) {

        return citaService.guardarCita(cita);
    }

    /**
     * Actualiza una cita existente.
     */
    @PutMapping("/{idCita}")
    public ResponseEntity<Cita> actualizarCita(
            @PathVariable @NonNull Long idCita,
            @Valid @RequestBody Cita cita) {

        Optional<Cita> citaExistente =
                citaService.buscarCitaPorId(idCita);

        if (citaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cita.setIdCita(idCita);

        return ResponseEntity.ok(
                citaService.guardarCita(cita));
    }

    /**
     * Elimina una cita.
     */
    @DeleteMapping("/{idCita}")
    public ResponseEntity<Void> eliminarCita(
            @PathVariable @NonNull Long idCita) {

        boolean eliminado =
                citaService.eliminarCita(idCita);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}