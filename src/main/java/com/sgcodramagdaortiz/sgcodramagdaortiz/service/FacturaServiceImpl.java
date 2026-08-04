package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.FacturaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;


/**
 * ============================================================
 * FACTURASERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Implementación de la lógica del módulo Facturación.
 *
 * ============================================================
 */

@Service
public class FacturaServiceImpl
        implements FacturaService {


    private final FacturaRepository facturaRepository;

    private final PacienteRepository pacienteRepository;

    private final CitaRepository citaRepository;



    public FacturaServiceImpl(
            FacturaRepository facturaRepository,
            PacienteRepository pacienteRepository,
            CitaRepository citaRepository) {


        this.facturaRepository = facturaRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;

    }



    @Override
    public List<Factura> listarFacturas() {

        return facturaRepository.findAll();

    }



    @Override
    public Optional<Factura> buscarFacturaPorId(
            Long idFactura) {

        return facturaRepository.findById(idFactura);

    }



    @Override
    public Factura guardarFactura(
            Factura factura) {


        /*
         * Validar paciente
         */
/*
 * Validar cita
 */
Cita cita =
        citaRepository.findById(
                factura.getCita()
                        .getIdCita()
        )
        .orElseThrow(
            () -> new RuntimeException(
                "La cita no existe"
            )
        );


/*
 * El paciente se obtiene directamente
 * desde la cita seleccionada.
 *
 * Esto evita generar facturas asociadas
 * a pacientes diferentes.
 */
Paciente paciente =
        cita.getPaciente();


factura.setPaciente(paciente);

factura.setCita(cita);



        if (factura.getFecha() == null) {

            factura.setFecha(
                    LocalDate.now()
            );

        }



        if (factura.getEstadoPago() == null
                || factura.getEstadoPago().isBlank()) {

            factura.setEstadoPago(
                    "PENDIENTE"
            );

        }



        /*
         * Si no viene total,
         * se inicializa en cero.
         *
         * Posteriormente se calculará
         * con los detalles de factura.
         */

        if (factura.getTotal() == null) {

            factura.setTotal(0);

        }



        return facturaRepository.save(factura);

    }



    @Override
    public Optional<Factura> actualizarFactura(
            Long idFactura,
            Factura factura) {


        Optional<Factura> existente =
                facturaRepository.findById(idFactura);



        if (existente.isEmpty()) {

            return Optional.empty();

        }



        Factura actual =
                existente.get();



        actual.setEstadoPago(
                factura.getEstadoPago()
        );


        actual.setTotal(
                factura.getTotal()
        );


        return Optional.of(
                facturaRepository.save(actual)
        );

    }



    @Override
    public boolean eliminarFactura(
            Long idFactura) {


        if (facturaRepository.existsById(idFactura)) {

            facturaRepository.deleteById(idFactura);

            return true;

        }


        return false;

    }

}