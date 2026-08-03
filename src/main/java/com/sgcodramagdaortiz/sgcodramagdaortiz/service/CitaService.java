package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;


/**
 * ============================================================
 * CITASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * CONSULTORIO ODONTOLÓGICO DRA. MAGDA ORTIZ
 * ============================================================
 *
 * Servicio encargado de la lógica de negocio de las citas.
 *
 * ============================================================
 */
@Service
public class CitaService {


    private final CitaRepository citaRepository;

    private final PacienteRepository pacienteRepository;

    private final ProfesionalRepository profesionalRepository;

    private final ServicioRepository servicioRepository;


    public CitaService(
            CitaRepository citaRepository,
            PacienteRepository pacienteRepository,
            ProfesionalRepository profesionalRepository,
            ServicioRepository servicioRepository) {

        this.citaRepository = citaRepository;
        this.pacienteRepository = pacienteRepository;
        this.profesionalRepository = profesionalRepository;
        this.servicioRepository = servicioRepository;

    }



    public List<Cita> listarCitas() {

        return citaRepository.findAll();

    }



    public Optional<Cita> buscarCitaPorId(
            @NonNull Long idCita) {

        return citaRepository.findById(idCita);

    }



    public Cita guardarCita(
            @NonNull Cita cita) {


        // Validar paciente

        Paciente paciente =
                pacienteRepository.findById(
                        cita.getPaciente().getIdPaciente()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El paciente no existe"
                    )
                );


        // Validar profesional

        Profesional profesional =
                profesionalRepository.findById(
                        cita.getProfesional().getIdProfesional()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El profesional no existe"
                    )
                );


        // Validar servicio

        Servicio servicio =
                servicioRepository.findById(
                        cita.getServicio().getIdServicio()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "El servicio no existe"
                    )
                );


        // Asignar entidades completas

        cita.setPaciente(paciente);

        cita.setProfesional(profesional);

        cita.setServicio(servicio);


        // Tomar duración desde el servicio

        cita.setDuracionMin(
                servicio.getDuracionMin()
        );


        // Fechas automáticas

        if (cita.getFechaCreacion() == null) {

            cita.setFechaCreacion(
                    LocalDate.now()
            );

        }


        cita.setFechaModificacion(
                LocalDate.now()
        );


        // Estado inicial

        if (cita.getEstado() == null ||
                cita.getEstado().isBlank()) {

            cita.setEstado("PROGRAMADA");

        }


        return citaRepository.save(cita);

    }



    public boolean eliminarCita(
            @NonNull Long idCita) {


        if (citaRepository.existsById(idCita)) {

            citaRepository.deleteById(idCita);

            return true;

        }


        return false;

    }

}