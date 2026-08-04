package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.DetalleFacturaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.FacturaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;


/**
 * ============================================================
 * DETALLEFACTURASERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Implementación del módulo DetalleFactura.
 *
 * Funciones:
 *
 * - Validar factura.
 * - Validar servicio.
 * - Calcular subtotal.
 * - Actualizar total de factura.
 *
 * Fórmula:
 *
 * subtotal = cantidad * precio servicio
 *
 * total factura = suma de subtotales
 *
 * ============================================================
 */

@Service
public class DetalleFacturaServiceImpl
        implements DetalleFacturaService {



    private final DetalleFacturaRepository detalleRepository;

    private final FacturaRepository facturaRepository;

    private final ServicioRepository servicioRepository;



    public DetalleFacturaServiceImpl(
            DetalleFacturaRepository detalleRepository,
            FacturaRepository facturaRepository,
            ServicioRepository servicioRepository) {


        this.detalleRepository = detalleRepository;

        this.facturaRepository = facturaRepository;

        this.servicioRepository = servicioRepository;

    }



    @Override
    public List<DetalleFactura> listarDetalles() {

        return detalleRepository.findAll();

    }



    @Override
    public Optional<DetalleFactura> buscarDetallePorId(
            Long idDetalle) {

        return detalleRepository.findById(idDetalle);

    }



    @Override
    public DetalleFactura guardarDetalle(
            DetalleFactura detalleFactura) {



        /*
         * Validar factura
         */
        Factura factura =
                facturaRepository.findById(
                        detalleFactura.getFactura()
                                .getIdFactura()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "La factura no existe"
                    )
                );



        /*
         * Validar servicio
         */
        Servicio servicio =
                servicioRepository.findById(
                        detalleFactura.getServicio()
                                .getIdServicio()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El servicio no existe"
                    )
                );



        detalleFactura.setFactura(factura);

        detalleFactura.setServicio(servicio);



        /*
         * Cantidad por defecto.
         */
        if (detalleFactura.getCantidad() == null
                || detalleFactura.getCantidad() <= 0) {

            detalleFactura.setCantidad(1);

        }



        /*
         * Calcular subtotal automáticamente.
         */
        detalleFactura.setSubtotal(
                detalleFactura.getCantidad()
                *
                servicio.getPrecio()
        );



        /*
         * Guardar detalle.
         */
        DetalleFactura guardado =
                detalleRepository.save(detalleFactura);



        /*
         * Actualizar total de la factura.
         */
        actualizarTotalFactura(factura);



        return guardado;

    }





    /**
     * Recalcula el total de una factura
     * sumando todos sus detalles.
     */
    private void actualizarTotalFactura(
            Factura factura) {


        List<DetalleFactura> detalles =
                detalleRepository.findAll()
                .stream()

                .filter(
                    detalle ->
                        detalle.getFactura()
                        .getIdFactura()
                        .equals(
                            factura.getIdFactura()
                        )
                )

                .toList();



        int total = detalles.stream()

                .mapToInt(
                    detalle ->
                        detalle.getSubtotal()
                )

                .sum();



        factura.setTotal(total);



        facturaRepository.save(factura);

    }





    @Override
    public Optional<DetalleFactura> actualizarDetalle(
            Long idDetalle,
            DetalleFactura detalleFactura) {


        Optional<DetalleFactura> existente =
                detalleRepository.findById(idDetalle);



        if (existente.isEmpty()) {

            return Optional.empty();

        }



        DetalleFactura actual =
                existente.get();



        actual.setCantidad(
                detalleFactura.getCantidad()
        );


        return Optional.of(
                detalleRepository.save(actual)
        );

    }





    @Override
    public boolean eliminarDetalle(
            Long idDetalle) {


        if (detalleRepository.existsById(idDetalle)) {


            detalleRepository.deleteById(idDetalle);


            return true;

        }


        return false;

    }

}