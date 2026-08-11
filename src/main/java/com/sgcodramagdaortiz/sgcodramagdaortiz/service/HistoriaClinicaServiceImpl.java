/*
============================================================
HistoriaClinicaServiceImpl.java

Sistema de Gestión de Citas Odontológicas

Implementación de la lógica del módulo
Historia Clínica.

Incluye:

- Gestión de historias clínicas.
- Validación de relaciones.
- Obtención de pacientes atendidos.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.service;



import java.time.LocalDate;

import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import java.util.Optional;



import org.springframework.stereotype.Service;



import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.PacienteAtendidoResponse;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;



import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.HistoriaClinicaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;





@Service
public class HistoriaClinicaServiceImpl

        implements HistoriaClinicaService {






    private final HistoriaClinicaRepository historiaRepository;


    private final PacienteRepository pacienteRepository;


    private final ProfesionalRepository profesionalRepository;


    private final CitaRepository citaRepository;







    public HistoriaClinicaServiceImpl(

            HistoriaClinicaRepository historiaRepository,

            PacienteRepository pacienteRepository,

            ProfesionalRepository profesionalRepository,

            CitaRepository citaRepository

    ) {


        this.historiaRepository = historiaRepository;

        this.pacienteRepository = pacienteRepository;

        this.profesionalRepository = profesionalRepository;

        this.citaRepository = citaRepository;


    }









    @Override
    public List<HistoriaClinica> listarHistorias() {


        return historiaRepository.findAll();


    }









    @Override
    public Optional<HistoriaClinica> buscarHistoriaPorId(

            Long idHistoria

    ) {


        return historiaRepository.findById(idHistoria);


    }









    @Override
    public HistoriaClinica guardarHistoria(

            HistoriaClinica historiaClinica

    ) {



        Paciente paciente =

                pacienteRepository.findById(

                        historiaClinica
                        .getPaciente()
                        .getIdPaciente()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "El paciente no existe"

                    )

                );








        Profesional profesional =

                profesionalRepository.findById(

                        historiaClinica
                        .getProfesional()
                        .getIdProfesional()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "El profesional no existe"

                    )

                );








        Cita cita =

                citaRepository.findById(

                        historiaClinica
                        .getCita()
                        .getIdCita()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "La cita no existe"

                    )

                );






        /*
        ====================================================
        VALIDACIÓN DE NEGOCIO

        Una historia clínica solamente puede
        crearse para una cita atendida.

        ====================================================
        */


        if(

            !"ASISTIDA".equals(

                    cita.getEstado()

            )

        ){

            throw new RuntimeException(

                    "Solo se pueden crear historias clínicas para citas asistidas."

            );

        }







        historiaClinica.setPaciente(

                paciente

        );


        historiaClinica.setProfesional(

                profesional

        );


        historiaClinica.setCita(

                cita

        );







        if(

            historiaClinica.getFecha() == null

        ){

            historiaClinica.setFecha(

                    LocalDate.now()

            );

        }







        return historiaRepository.save(

                historiaClinica

        );


    }









    @Override
    public Optional<HistoriaClinica> actualizarHistoria(

            Long idHistoria,

            HistoriaClinica historiaClinica

    ) {



        Optional<HistoriaClinica> existente =

                historiaRepository.findById(

                        idHistoria

                );




        if(

            existente.isEmpty()

        ){

            return Optional.empty();

        }







        HistoriaClinica actual =

                existente.get();







        actual.setMotivoConsulta(

                historiaClinica.getMotivoConsulta()

        );



        actual.setDiagnostico(

                historiaClinica.getDiagnostico()

        );



        actual.setTratamiento(

                historiaClinica.getTratamiento()

        );



        actual.setObservaciones(

                historiaClinica.getObservaciones()

        );







        return Optional.of(

                historiaRepository.save(

                        actual

                )

        );


    }









    @Override
    public boolean eliminarHistoria(

            Long idHistoria

    ){



        if(

            historiaRepository.existsById(

                    idHistoria

            )

        ){


            historiaRepository.deleteById(

                    idHistoria

            );


            return true;


        }




        return false;


    }









    /*
    ========================================================
    PACIENTES ATENDIDOS

    Obtiene pacientes únicos que tienen
    una cita en estado ASISTIDA.

    Utilizado por:

    Historia Clínica.

    ========================================================
    */


    @Override
    public List<PacienteAtendidoResponse>
    listarPacientesAtendidos(){



        Map<Long, PacienteAtendidoResponse> pacientes =

                new LinkedHashMap<>();







        List<Cita> citas =

                citaRepository.findByEstado(

                        "ASISTIDA"

                );







        for(Cita cita : citas){



            Paciente paciente =

                    cita.getPaciente();






            pacientes.put(

                    paciente.getIdPaciente(),

                    new PacienteAtendidoResponse(

                            paciente.getIdPaciente(),

                            paciente.getNombre(),

                            paciente.getApellido()

                    )

            );


        }







        return new ArrayList<>(

                pacientes.values()

        );


    }





}