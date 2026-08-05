package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;


import java.util.List;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
 * Controlador REST del módulo Facturación.
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
            FacturaService facturaService) {

        this.facturaService = facturaService;

    }





    /**
     * Lista facturas.
     */
    @GetMapping
    public List<Factura> listarFacturas() {

        return facturaService.listarFacturas();

    }






    /**
     * Buscar factura por ID.
     */
    @GetMapping("/{idFactura}")
    public ResponseEntity<Factura> buscarFactura(
            @PathVariable @NonNull Long idFactura) {



        Optional<Factura> factura =
                facturaService.buscarFacturaPorId(
                        idFactura
                );



        return factura
                .map(ResponseEntity::ok)
                .orElseGet(
                    () ->
                    ResponseEntity.notFound().build()
                );

    }







    /**
     * ========================================================
     * NUEVO ENDPOINT
     *
     * Crear factura completa:
     *
     * - Factura
     * - Detalles
     * - Total automático
     *
     * POST:
     *
     * /api/facturas/completa
     *
     * ========================================================
     */
    @PostMapping("/completa")
    public ResponseEntity<?> crearFacturaCompleta(
            @Valid @RequestBody FacturaRequestDTO request) {


        try {


            Factura factura =
                    facturaService.crearFacturaCompleta(
                            request
                    );



            return ResponseEntity.ok(
                    factura
            );


        } catch(RuntimeException e) {


            return ResponseEntity
                    .badRequest()
                    .body(
                        e.getMessage()
                    );

        }


    }






    /**
     * Método tradicional.
     *
     * Se mantiene para compatibilidad.
     */
    @PostMapping
    public ResponseEntity<?> guardarFactura(
            @Valid @RequestBody Factura factura) {



        try {


            Factura guardada =
                    facturaService.guardarFactura(
                            factura
                    );


            return ResponseEntity.ok(
                    guardada
            );



        } catch(RuntimeException e) {


            return ResponseEntity
                    .badRequest()
                    .body(
                        e.getMessage()
                    );


        }


    }







    @PutMapping("/{idFactura}")
    public ResponseEntity<?> actualizarFactura(
            @PathVariable @NonNull Long idFactura,
            @Valid @RequestBody Factura factura) {



        Optional<Factura> actualizada =
                facturaService.actualizarFactura(
                        idFactura,
                        factura
                );



        return actualizada
                .map(ResponseEntity::ok)
                .orElseGet(
                    () ->
                    ResponseEntity.notFound().build()
                );


    }







    @DeleteMapping("/{idFactura}")
    public ResponseEntity<Void> eliminarFactura(
            @PathVariable @NonNull Long idFactura) {



        boolean eliminado =
                facturaService.eliminarFactura(
                        idFactura
                );



        if(eliminado) {


            return ResponseEntity
                    .noContent()
                    .build();


        }



        return ResponseEntity
                .notFound()
                .build();


    }


}