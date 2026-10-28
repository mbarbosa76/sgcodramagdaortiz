package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;


import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.ActualizarFacturaDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.FacturaRequestDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.FacturaService;


import jakarta.validation.Valid;


/**
 * ============================================================
 * FACTURACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST encargado del módulo Facturación.
 *
 * Responsabilidades:
 *
 * - Consultar facturas.
 * - Crear facturas.
 * - Actualizar información administrativa.
 * - Eliminar facturas.
 *
 * ============================================================
 */


@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "http://localhost:5173")
@Validated
public class FacturaController {



    private final FacturaService facturaService;



    public FacturaController(
            FacturaService facturaService
    ){

        this.facturaService = facturaService;

    }





    /**
     * Lista todas las facturas registradas.
     */
    @GetMapping
    public List<Factura> listarFacturas(){

        return facturaService.listarFacturas();

    }





    /**
     * Busca una factura mediante su ID.
     */
    @GetMapping("/{idFactura}")
    public ResponseEntity<Factura> buscarFactura(
            @PathVariable @NonNull Long idFactura
    ){

        Optional<Factura> factura =
                facturaService.buscarFacturaPorId(idFactura);


        return factura
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );

    }





    /**
     * ============================================================
     * CREAR FACTURA COMPLETA
     * ============================================================
     *
     * Genera:
     *
     * - Factura.
     * - Detalles.
     * - Total.
     */
    @PostMapping("/completa")
    public ResponseEntity<?> crearFacturaCompleta(
            @Valid @RequestBody FacturaRequestDTO request
    ){

        try {

            Factura factura =
                    facturaService.crearFacturaCompleta(request);


            return ResponseEntity.ok(factura);


        } catch(RuntimeException e){

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }





    /**
     * Método tradicional para guardar factura.
     */
    @PostMapping
    public ResponseEntity<?> guardarFactura(
            @Valid @RequestBody Factura factura
    ){

        try {

            Factura guardada =
                    facturaService.guardarFactura(factura);


            return ResponseEntity.ok(guardada);


        } catch(RuntimeException e){

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }





    /**
     * ============================================================
     * ACTUALIZAR FACTURA
     * ============================================================
     *
     * IMPORTANTE:
     *
     * Antes recibía una entidad Factura completa.
     * Esto generaba errores porque paciente y cita
     * son relaciones obligatorias.
     *
     * Ahora recibe ActualizarFacturaDTO:
     *
     * - Fecha.
     * - Estado de pago.
     * - Total.
     *
     * Las relaciones históricas permanecen intactas.
     */
    @PutMapping("/{idFactura}")
    public ResponseEntity<?> actualizarFactura(
            @PathVariable @NonNull Long idFactura,
            @RequestBody ActualizarFacturaDTO request
    ){

        try {


            Optional<Factura> actualizada =
                    facturaService.actualizarFactura(
                            idFactura,
                            request
                    );


            return actualizada
                    .map(ResponseEntity::ok)
                    .orElseGet(
                            () -> ResponseEntity.notFound().build()
                    );


        } catch(RuntimeException e){


            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );

        }

    }





    /**
     * Elimina una factura.
     */
    @DeleteMapping("/{idFactura}")
    public ResponseEntity<Void> eliminarFactura(
            @PathVariable @NonNull Long idFactura
    ){


        boolean eliminado =
                facturaService.eliminarFactura(idFactura);



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