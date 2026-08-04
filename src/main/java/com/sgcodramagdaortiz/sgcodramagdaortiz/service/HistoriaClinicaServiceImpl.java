package com.sgcodramagdaortiz.sgcodramagdaortiz.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.HistoriaClinica;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.HistoriaClinicaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;


/**
 * ============================================================
 * HISTORIACLINICASERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Implementación de la lógica del módulo Historia Clínica.
 *
 * ============================================================
 */

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
            CitaRepository citaRepository) {

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
            Long idHistoria) {

        return historiaRepository.findById(idHistoria);

    }



    @Override
    public HistoriaClinica guardarHistoria(
            HistoriaClinica historiaClinica) {


        Paciente paciente =
                pacienteRepository.findById(
                        historiaClinica.getPaciente()
                                .getIdPaciente()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El paciente no existe"
                    )
                );


        Profesional profesional =
                profesionalRepository.findById(
                        historiaClinica.getProfesional()
                                .getIdProfesional()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El profesional no existe"
                    )
                );


        Cita cita =
                citaRepository.findById(
                        historiaClinica.getCita()
                                .getIdCita()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "La cita no existe"
                    )
                );



        historiaClinica.setPaciente(paciente);

        historiaClinica.setProfesional(profesional);

        historiaClinica.setCita(cita);



        if (historiaClinica.getFecha() == null) {

            historiaClinica.setFecha(
                    LocalDate.now()
            );

        }



        return historiaRepository.save(historiaClinica);

    }



    @Override
    public Optional<HistoriaClinica> actualizarHistoria(
            Long idHistoria,
            HistoriaClinica historiaClinica) {


        Optional<HistoriaClinica> existente =
                historiaRepository.findById(idHistoria);



        if (existente.isEmpty()) {

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
                historiaRepository.save(actual)
        );

    }



    @Override
    public boolean eliminarHistoria(
            Long idHistoria) {


        if (historiaRepository.existsById(idHistoria)) {

            historiaRepository.deleteById(idHistoria);

            return true;

        }


        return false;

    }

}