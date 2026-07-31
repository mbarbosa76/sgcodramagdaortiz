package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.ServicioService;

/**
 * Controlador REST para consultar los servicios odontológicos.
 */
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:5173")
public class ServicioController {

    private final ServicioService servicioService;

    /**
     * Constructor.
     */
    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    /**
     * Lista todos los servicios.
     *
     * GET /api/servicios
     */
    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.listarServicios();
    }

    /**
     * Busca un servicio por ID.
     *
     * GET /api/servicios/1
     */
    @GetMapping("/{idServicio}")
    public ResponseEntity<Servicio> buscarServicioPorId(
            @PathVariable Long idServicio) {

        Optional<Servicio> servicio =
                servicioService.buscarServicioPorId(idServicio);

        return servicio
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}