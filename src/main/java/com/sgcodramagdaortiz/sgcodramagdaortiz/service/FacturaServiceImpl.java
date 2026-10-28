package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.ActualizarFacturaDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.FacturaRequestDTO;


import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.DetalleFactura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Factura;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;


import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.DetalleFacturaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.FacturaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;



/**
 * ============================================================
 * FACTURASERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Lógica del módulo Facturación.
 *
 * Incluye:
 *
 * - Creación de facturas.
 * - Generación automática del número.
 * - Creación de detalles.
 * - Cálculo automático del total.
 *
 * ============================================================
 */


@Service
public class FacturaServiceImpl
        implements FacturaService {



    private final FacturaRepository facturaRepository;

    private final PacienteRepository pacienteRepository;

    private final CitaRepository citaRepository;

    private final DetalleFacturaRepository detalleFacturaRepository;

    private final ServicioRepository servicioRepository;



    public FacturaServiceImpl(

            FacturaRepository facturaRepository,

            PacienteRepository pacienteRepository,

            CitaRepository citaRepository,

            DetalleFacturaRepository detalleFacturaRepository,

            ServicioRepository servicioRepository

    ) {


        this.facturaRepository = facturaRepository;

        this.pacienteRepository = pacienteRepository;

        this.citaRepository = citaRepository;

        this.detalleFacturaRepository =
                detalleFacturaRepository;

        this.servicioRepository =
                servicioRepository;

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






    /**
     * Método tradicional.
     *
     * Se mantiene para compatibilidad.
     */
    @Override
    public Factura guardarFactura(
            Factura factura) {



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



        factura.setCita(cita);

        factura.setPaciente(
                cita.getPaciente()
        );



        prepararFactura(
                factura
        );



        return facturaRepository.save(
                factura
        );

    }





    /**
     * ============================================================
     * CREAR FACTURA COMPLETA
     *
     * Recibe:
     *
     * - Paciente.
     * - Cita.
     * - Servicios seleccionados.
     *
     * Genera:
     *
     * - Factura.
     * - Detalles.
     * - Total automático.
     *
     * ============================================================
     */
    @Override
    @Transactional
    public Factura crearFacturaCompleta(
            FacturaRequestDTO request) {



        Cita cita =
                citaRepository.findById(
                        request.getIdCita()
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "La cita no existe"
                        )
                );



        Paciente paciente =
                cita.getPaciente();




        Factura factura =
                new Factura();



        factura.setPaciente(
                paciente
        );


        factura.setCita(
                cita
        );



        factura.setEstadoPago(
                request.getEstadoPago()
        );



        prepararFactura(
                factura
        );



        factura.setTotal(0);



        factura =
                facturaRepository.save(
                        factura
                );




        int total = 0;




        if (request.getServicios() != null) {



            for(Long idServicio :
                    request.getServicios()) {



                Servicio servicio =
                        servicioRepository.findById(
                                idServicio
                        )
                        .orElseThrow(
                            () -> new RuntimeException(
                                "Servicio no encontrado"
                            )
                        );



                DetalleFactura detalle =
                        new DetalleFactura();



                detalle.setFactura(
                        factura
                );



                detalle.setServicio(
                        servicio
                );



                detalle.setCantidad(
                        1
                );



                detalle.setSubtotal(
                        servicio.getPrecio()
                );



                total += servicio.getPrecio();



                detalleFacturaRepository.save(
                        detalle
                );


            }

        }



        factura.setTotal(
                total
        );



        return facturaRepository.save(
                factura
        );

    }






    /**
     * Prepara datos automáticos.
     */
    private void prepararFactura(
            Factura factura) {



        if(factura.getNumeroFactura()
                == null
                ||
            factura.getNumeroFactura()
                .isBlank()) {



            factura.setNumeroFactura(
                    generarNumeroFactura()
            );


        }




        if(factura.getFecha()
                == null) {



            factura.setFecha(
                    LocalDate.now()
            );

        }


        /*
          ============================================================
          VALIDACIÓN: NO SE PERMITEN FACTURAS CON FECHA FUTURA
          ============================================================
          Contablemente, una factura documenta un servicio ya
          prestado (o que se está prestando en este momento), no
          uno que aún no ha ocurrido. Por eso no debería poder
          registrarse con una fecha posterior a hoy.
        */
        validarFechaNoFutura(
                factura.getFecha()
        );




        if(factura.getEstadoPago()
                == null
                ||
            factura.getEstadoPago()
                .isBlank()) {



            factura.setEstadoPago(
                    "PENDIENTE"
            );

        }



    }






    /**
     * ============================================================
     * VALIDAR QUE LA FECHA NO SEA FUTURA
     * ============================================================
     * Se usa tanto al crear como al editar una factura.
     * Lanza una excepción (que el controlador traduce en un
     * HTTP 400) si la fecha recibida es posterior a hoy.
     */
    private void validarFechaNoFutura(
            LocalDate fecha) {

        if(fecha != null
                &&
            fecha.isAfter(LocalDate.now())) {

            throw new RuntimeException(
                    "La fecha de la factura no puede ser posterior a hoy."
            );

        }

    }




    private String generarNumeroFactura() {



        Optional<Factura> ultima =
                facturaRepository
                .findTopByOrderByNumeroFacturaDesc();



        long siguiente = 1;



        if(ultima.isPresent()) {



            String numero =
                    ultima.get()
                    .getNumeroFactura();



            if(numero != null
                    &&
               numero.startsWith("FAC-")) {



                siguiente =
                    Long.parseLong(
                        numero.substring(4)
                    )
                    + 1;


            }


        }



        return String.format(
                "FAC-%04d",
                siguiente
        );


    }






  /**
 * ============================================================
 * ACTUALIZAR FACTURA
 * ============================================================
 *
 * Actualiza únicamente información administrativa
 * de una factura existente.
 *
 * Campos permitidos:
 *
 * - Estado de pago.
 * - Total.
 * - Fecha.
 *
 * No modifica:
 *
 * - Paciente.
 * - Cita.
 * - Servicios asociados.
 *
 * Esto protege la integridad histórica de la factura.
 *
 * ============================================================
 */
@Override
public Optional<Factura> actualizarFactura(
        Long idFactura,
        ActualizarFacturaDTO request
) {


    Optional<Factura> existente =
            facturaRepository.findById(
                    idFactura
            );


    /*
     * Si la factura no existe,
     * se retorna vacío para que el controlador
     * responda HTTP 404.
     */
    if(existente.isEmpty()) {

        return Optional.empty();

    }



    Factura actual =
            existente.get();



    /*
     * Actualización del estado del pago.
     */
    if(request.getEstadoPago() != null) {


        actual.setEstadoPago(
                request.getEstadoPago()
        );

    }




    /*
     * Actualización del valor total.
     */
    if(request.getTotal() != null) {


        actual.setTotal(
                request.getTotal()
        );

    }





    /*
     * Actualización de fecha.
     *
     * No permite registrar fechas futuras.
     */
    if(request.getFecha() != null) {


        validarFechaNoFutura(
                request.getFecha()
        );


        actual.setFecha(
                request.getFecha()
        );

    }




    return Optional.of(
            facturaRepository.save(actual)
    );


}



    @Override
    public boolean eliminarFactura(
            Long idFactura) {



        if(facturaRepository.existsById(
                idFactura)) {



            facturaRepository.deleteById(
                    idFactura
            );


            return true;


        }



        return false;


    }


}