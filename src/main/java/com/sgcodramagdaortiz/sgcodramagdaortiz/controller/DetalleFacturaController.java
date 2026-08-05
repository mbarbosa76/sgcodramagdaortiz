package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.DetalleFacturaService;

import jakarta.validation.Valid;


/**
 * ============================================================
 * DETALLEFACTURACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST encargado de gestionar los detalles
 * de una factura.
 *
 * Endpoints:
 *
 * GET     /api/detalles-factura
 * GET     /api/detalles-factura/{id}
 * POST    /api/detalles-factura
 * PUT     /api/detalles-factura/{id}
 * DELETE  /api/detalles-factura/{id}
 *
 * ============================================================
 */


@RestController
@RequestMapping("/api/detalles-factura")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class DetalleFacturaController {


    private final DetalleFacturaService detalleService;



    public DetalleFacturaController(
            DetalleFacturaService detalleService) {

        this.detalleService = detalleService;

    }



    /**
     * Listar detalles.
     */
    @GetMapping
    public List<DetalleFactura> listarDetalles() {

        return detalleService.listarDetalles();

    }



    /**
     * Buscar detalle por ID.
     */
    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetalleFactura> buscarDetalle(
            @PathVariable @NonNull Long idDetalle) {


        Optional<DetalleFactura> detalle =
                detalleService.buscarDetallePorId(idDetalle);



        return detalle
                .map(ResponseEntity::ok)
                .orElseGet(
                    () -> ResponseEntity.notFound().build()
                );

    }



    /**
     * Crear detalle.
     */
    @PostMapping
    public ResponseEntity<?> guardarDetalle(
            @Valid @RequestBody DetalleFactura detalleFactura) {


        try {


            DetalleFactura guardado =
                    detalleService.guardarDetalle(
                            detalleFactura
                    );


            return ResponseEntity.ok(
                    guardado
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
     * Actualizar detalle.
     */
    @PutMapping("/{idDetalle}")
    public ResponseEntity<?> actualizarDetalle(
            @PathVariable @NonNull Long idDetalle,
            @Valid @RequestBody DetalleFactura detalleFactura) {



        Optional<DetalleFactura> actualizado =
                detalleService.actualizarDetalle(
                        idDetalle,
                        detalleFactura
                );



        return actualizado
                .map(ResponseEntity::ok)
                .orElseGet(
                    () -> ResponseEntity.notFound().build()
                );

    }

/**
 * Buscar detalles por factura.
 *
 * GET:
 * /api/detalles-factura/factura/{idFactura}
 */
@GetMapping("/factura/{idFactura}")
public ResponseEntity<List<DetalleFactura>> listarPorFactura(
        @PathVariable Long idFactura) {


    return ResponseEntity.ok(
            detalleService.listarPorFactura(
                    idFactura
            )
    );

}

    /**
     * Eliminar detalle.
     */
    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable @NonNull Long idDetalle) {



        boolean eliminado =
                detalleService.eliminarDetalle(
                        idDetalle
                );



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