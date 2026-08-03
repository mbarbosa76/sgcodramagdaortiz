package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa LocalDate para manejar fechas
import java.time.LocalDate;

// Importa List y Optional
import java.util.List;
import java.util.Optional;

// Importa anotación Service
import org.springframework.stereotype.Service;

// Importa entidad Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

// Importa repositorio Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;


/**
 * ============================================================
 * PROFESIONALSERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Implementación de la interfaz ProfesionalService.
 *
 * Contiene la lógica de negocio para:
 *
 * - Consultar profesionales.
 * - Registrar profesionales.
 * - Actualizar profesionales.
 * - Eliminar profesionales.
 *
 * ============================================================
 */

@Service
public class ProfesionalServiceImpl implements ProfesionalService {


    /**
     * Repositorio de profesionales.
     */
    private final ProfesionalRepository profesionalRepository;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param profesionalRepository repositorio de profesionales
     */
    public ProfesionalServiceImpl(
            ProfesionalRepository profesionalRepository) {

        this.profesionalRepository = profesionalRepository;

    }



    /**
     * Lista todos los profesionales registrados.
     */
    @Override
    public List<Profesional> listarProfesionales() {

        return profesionalRepository.findAll();

    }



    /**
     * Busca un profesional por ID.
     */
    @Override
    public Optional<Profesional> buscarProfesionalPorId(
            Long idProfesional) {

        return profesionalRepository.findById(idProfesional);

    }



    /**
     * Guarda un profesional nuevo.
     *
     * Si no tiene fecha de registro,
     * asigna la fecha actual.
     */
    @Override
    public Profesional guardarProfesional(
            Profesional profesional) {


        if (profesional.getFechaRegistro() == null) {

            profesional.setFechaRegistro(
                    LocalDate.now()
            );

        }


        if (profesional.getEstado() == null
                || profesional.getEstado().isBlank()) {

            profesional.setEstado("Activo");

        }


        return profesionalRepository.save(profesional);

    }



    /**
     * Actualiza un profesional existente.
     */
    @Override
    public Optional<Profesional> actualizarProfesional(
            Long idProfesional,
            Profesional profesional) {


        Optional<Profesional> profesionalExistente =
                profesionalRepository.findById(idProfesional);


        if (profesionalExistente.isEmpty()) {

            return Optional.empty();

        }


        Profesional actual =
                profesionalExistente.get();


        actual.setIdentificacion(
                profesional.getIdentificacion());

        actual.setNombre(
                profesional.getNombre());

        actual.setApellido(
                profesional.getApellido());

        actual.setEspecialidad(
                profesional.getEspecialidad());

        actual.setTelefono(
                profesional.getTelefono());

        actual.setCorreo(
                profesional.getCorreo());

        actual.setDireccion(
                profesional.getDireccion());

        actual.setRegistroProfesional(
                profesional.getRegistroProfesional());

        actual.setEstado(
                profesional.getEstado());

        actual.setDepartamento(
                profesional.getDepartamento());

        actual.setMunicipio(
                profesional.getMunicipio());


        return Optional.of(
                profesionalRepository.save(actual)
        );

    }



    /**
     * Elimina un profesional por ID.
     */
    @Override
    public boolean eliminarProfesional(
            Long idProfesional) {


        if (profesionalRepository.existsById(idProfesional)) {


            profesionalRepository.deleteById(
                    idProfesional
            );


            return true;

        }


        return false;

    }

}