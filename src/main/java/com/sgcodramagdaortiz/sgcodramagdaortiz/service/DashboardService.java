/*
============================================================
DashboardService.java

Sistema de Gestión de Citas Odontológicas

Servicio encargado de construir la información
mostrada en el Dashboard según el rol del usuario.

Responsabilidades:

- Generar indicadores del administrador.
- Generar indicadores del profesional.
- Generar indicadores del recepcionista.
- Generar gráficos según el rol.
- Aplicar filtros según usuario autenticado.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.service;



import org.springframework.stereotype.Service;



import java.util.ArrayList;

import java.util.List;



import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.CitasEstadoResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.FacturacionMensualResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardAdminResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardProfesionalResponse;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DashboardRecepcionResponse;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;



import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.HistoriaClinicaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.FacturaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;





@Service
public class DashboardService {




    /*
    ========================================================
    REPOSITORIOS UTILIZADOS

    Permiten consultar la información necesaria
    para construir indicadores y gráficos.

    ========================================================
    */


    private final UsuarioRepository usuarioRepository;


    private final PacienteRepository pacienteRepository;


    private final ProfesionalRepository profesionalRepository;


    private final CitaRepository citaRepository;


    private final HistoriaClinicaRepository historiaRepository;


    private final FacturaRepository facturaRepository;








    /*
    ========================================================
    CONSTRUCTOR

    Inyección de dependencias.

    ========================================================
    */


    public DashboardService(

            UsuarioRepository usuarioRepository,

            PacienteRepository pacienteRepository,

            ProfesionalRepository profesionalRepository,

            CitaRepository citaRepository,

            HistoriaClinicaRepository historiaRepository,

            FacturaRepository facturaRepository

    ){


        this.usuarioRepository =
                usuarioRepository;


        this.pacienteRepository =
                pacienteRepository;


        this.profesionalRepository =
                profesionalRepository;


        this.citaRepository =
                citaRepository;


        this.historiaRepository =
                historiaRepository;


        this.facturaRepository =
                facturaRepository;


    }









    /*
    ========================================================
    DASHBOARD ADMINISTRADOR

    Devuelve información global
    del sistema.

    ========================================================
    */


    public DashboardAdminResponse obtenerDashboardAdmin(){



        DashboardAdminResponse respuesta =

                new DashboardAdminResponse();




        respuesta.setUsuarios(

                usuarioRepository.count()

        );



        respuesta.setPacientes(

                pacienteRepository.count()

        );



        respuesta.setProfesionales(

                profesionalRepository.count()

        );



        respuesta.setCitas(

                citaRepository.count()

        );



        respuesta.setHistorias(

                historiaRepository.count()

        );





        /*
        ====================================================
        CÁLCULO DE FACTURACIÓN TOTAL

        Suma todos los valores
        almacenados en facturas.

        ====================================================
        */


        Integer totalFacturacion =


                facturaRepository
                .findAll()
                .stream()
                .mapToInt(

                    factura ->


                    factura.getTotal() == null

                    ?

                    0

                    :

                    factura.getTotal()

                )
                .sum();




        respuesta.setFacturacion(

                totalFacturacion

        );




        return respuesta;


    }









    /*
    ========================================================
    DASHBOARD PROFESIONAL

    Devuelve únicamente información
    relacionada con el profesional autenticado.

    ========================================================
    */


    public DashboardProfesionalResponse obtenerDashboardProfesional(

            String username

    ){



        DashboardProfesionalResponse respuesta =

                new DashboardProfesionalResponse();





        /*
        ====================================================
        BUSCAR USUARIO AUTENTICADO

        Obtiene usuario desde JWT.

        ====================================================
        */


        Usuario usuario =


                usuarioRepository
                .findByUsername(username)

                .orElseThrow(

                    () ->

                    new RuntimeException(

                        "Usuario no encontrado"

                    )

                );








        /*
        ====================================================
        BUSCAR PROFESIONAL ASOCIADO

        Relación:

        Usuario 1 ---- 1 Profesional

        ====================================================
        */


        Profesional profesional =


                profesionalRepository
                .findByUsuarioIdUsuario(

                        usuario.getIdUsuario()

                )

                .orElseThrow(

                    () ->

                    new RuntimeException(

                        "Profesional no asociado"

                    )

                );






        Long idProfesional =


                profesional.getIdProfesional();







        respuesta.setMisCitas(

                citaRepository
                .countByProfesionalIdProfesional(

                        idProfesional

                )

        );







        respuesta.setMisHistorias(

                historiaRepository
                .countByProfesionalIdProfesional(

                        idProfesional

                )

        );







        respuesta.setMisPacientes(

                historiaRepository
                .contarPacientesAtendidosPorProfesional(

                        idProfesional

                )

        );






        return respuesta;


    }

    /*
    ========================================================
    DASHBOARD RECEPCIONISTA

    Información operativa del consultorio.

    ========================================================
    */


    public DashboardRecepcionResponse obtenerDashboardRecepcionista(){



        DashboardRecepcionResponse respuesta =

                new DashboardRecepcionResponse();





        respuesta.setPacientes(

                pacienteRepository.count()

        );





        respuesta.setCitasPendientes(

                citaRepository
                .countByEstado(

                        "PROGRAMADA"

                )

        );





        respuesta.setFacturasPendientes(

                facturaRepository
                .findAll()
                .stream()
                .filter(

                    factura ->

                    "PENDIENTE"
                    .equals(

                            factura.getEstadoPago()

                    )

                )
                .count()

        );





        return respuesta;


    }









    /*
    ========================================================
    GRÁFICO GENERAL DE CITAS

    Utilizado por:

    Administrador.

    Devuelve todas las citas
    agrupadas por estado.

    ========================================================
    */


    public List<CitasEstadoResponse> obtenerCitasPorEstado(){



        List<CitasEstadoResponse> resultado =

                new ArrayList<>();





        List<Object[]> datos =

                citaRepository
                .contarCitasPorEstado();





        for(Object[] fila : datos){



            resultado.add(

                    new CitasEstadoResponse(

                            (String) fila[0],

                            ((Number) fila[1])
                            .longValue()

                    )

            );


        }





        return resultado;


    }









    /*
    ========================================================
    GRÁFICO DE CITAS DEL PROFESIONAL

    Devuelve únicamente las citas
    del profesional autenticado.

    ========================================================
    */


    public List<CitasEstadoResponse>
    obtenerCitasEstadoProfesional(

            String username

    ){



        Usuario usuario =


                usuarioRepository
                .findByUsername(username)

                .orElseThrow(

                    () ->

                    new RuntimeException(

                            "Usuario no encontrado"

                    )

                );






        Profesional profesional =


                profesionalRepository
                .findByUsuarioIdUsuario(

                        usuario.getIdUsuario()

                )

                .orElseThrow(

                    () ->

                    new RuntimeException(

                            "Profesional no asociado"

                    )

                );






        List<CitasEstadoResponse> resultado =

                new ArrayList<>();





        List<Object[]> datos =


                citaRepository
                .contarCitasPorEstadoProfesional(

                        profesional.getIdProfesional()

                );






        for(Object[] fila : datos){



            resultado.add(

                    new CitasEstadoResponse(

                            (String) fila[0],

                            ((Number) fila[1])
                            .longValue()

                    )

            );


        }





        return resultado;


    }









    /*
    ========================================================
    GRÁFICO DE CITAS PARA RECEPCIÓN

    Información operativa de agenda.

    No muestra información financiera.

    Utilizado por:

    Dashboard Recepcionista.

    ========================================================
    */


    public List<CitasEstadoResponse>
    obtenerCitasEstadoRecepcionista(){



        List<CitasEstadoResponse> resultado =

                new ArrayList<>();





List<Object[]> datos =

        citaRepository
        .contarCitasPorEstado();


        for(Object[] fila : datos){



            resultado.add(

                    new CitasEstadoResponse(

                            (String) fila[0],

                            ((Number) fila[1])
                            .longValue()

                    )

            );


        }





        return resultado;


    }









    /*
    ========================================================
    FACTURACIÓN MENSUAL

    Utilizada exclusivamente para
    gráficos administrativos.

    ========================================================
    */


    public List<FacturacionMensualResponse>
    obtenerFacturacionMensual(){



        List<FacturacionMensualResponse> resultado =

                new ArrayList<>();





        List<Object[]> datos =


                facturaRepository
                .obtenerFacturacionMensual();







        for(Object[] fila : datos){



            Integer numeroMes =


                    ((Number) fila[0])
                    .intValue();





            Integer total =


                    ((Number) fila[1])
                    .intValue();







            resultado.add(

                    new FacturacionMensualResponse(

                            obtenerNombreMes(numeroMes),

                            total

                    )

            );


        }





        return resultado;


    }









    /*
    ========================================================
    CONVERSIÓN DE MES NUMÉRICO A TEXTO

    Ejemplo:

    1 -> Enero

    2 -> Febrero

    ========================================================
    */


    private String obtenerNombreMes(

            Integer mes

    ){



        String[] nombres = {


                "Enero",

                "Febrero",

                "Marzo",

                "Abril",

                "Mayo",

                "Junio",

                "Julio",

                "Agosto",

                "Septiembre",

                "Octubre",

                "Noviembre",

                "Diciembre"


        };





        return nombres[mes - 1];


    }



}