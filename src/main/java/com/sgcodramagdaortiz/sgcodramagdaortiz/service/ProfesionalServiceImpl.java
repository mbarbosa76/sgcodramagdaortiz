package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa LocalDate para registrar la fecha de registro automáticamente
import java.time.LocalDate;

// Importa List y Optional
import java.util.List;
import java.util.Optional;

// Importa Service para marcar esta clase como componente de servicio en Spring
import org.springframework.stereotype.Service;

// Importa la entidad Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

// Importa el repositorio de Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;

/**
 * ============================================================
 * PROFESIONALSERVICEIMPL.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Implementación del servicio de profesionales.
 *
 * NOTA IMPORTANTE:
 *
 * En la actividad se pidió un archivo llamado
 * "Servicelmpl.java", pero esa es la forma abreviada
 * (y con una pequeña errata: "l" en vez de "I") de
 * referirse a "ProfesionalServiceImpl.java", que es el
 * nombre correcto según la convención de Java/Spring Boot
 * y el mismo patrón que ya usa este proyecto en
 * UsuarioServiceImpl.java.
 *
 * Contiene la lógica de negocio relacionada con el
 * registro, consulta, actualización y eliminación
 * de profesionales.
 */
@Service
public class ProfesionalServiceImpl implements ProfesionalService {

    /**
     * Repositorio de profesionales.
     * Se inyecta automáticamente mediante constructor.
     */
    private final ProfesionalRepository profesionalRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param profesionalRepository repositorio de profesionales
     */
    public ProfesionalServiceImpl(ProfesionalRepository profesionalRepository) {
        this.profesionalRepository = profesionalRepository;
    }


    /**
     * Lista todos los profesionales.
     *
     * @return lista de profesionales
     */
    @Override
    public List<Profesional> listarProfesionales() {

        return profesionalRepository.findAll();

    }


    /**
     * Busca un profesional por su ID.
     *
     * @param idProfesional identificador del profesional
     * @return profesional encontrado
     */
    @Override
    public Optional<Profesional> buscarProfesionalPorId(Long idProfesional) {

        return profesionalRepository.findById(idProfesional);

    }


    /**
     * Registra un nuevo profesional.
     *
     * Si no trae fecha de registro, se asigna
     * automáticamente la fecha actual.
     *
     * Si no trae estado, se asigna "Activo" por defecto
     * (esto también ocurre a nivel de la entidad, pero
     * se refuerza aquí por claridad).
     *
     * @param profesional profesional a registrar
     * @return profesional guardado
     */
    @Override
    public Profesional guardarProfesional(Profesional profesional) {

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
     *
     * @param idProfesional identificador del profesional
     * @param profesional datos nuevos
     * @return profesional actualizado, si existía
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


        Profesional actual = profesionalExistente.get();

        actual.setIdentificacion(profesional.getIdentificacion());
        actual.setNombre(profesional.getNombre());
        actual.setApellido(profesional.getApellido());
        actual.setEspecialidad(profesional.getEspecialidad());
        actual.setTelefono(profesional.getTelefono());
        actual.setCorreo(profesional.getCorreo());
        actual.setDireccion(profesional.getDireccion());
        actual.setRegistroProfesional(profesional.getRegistroProfesional());
        actual.setEstado(profesional.getEstado());
        actual.setDepartamento(profesional.getDepartamento());
        actual.setMunicipio(profesional.getMunicipio());

        return Optional.of(
            profesionalRepository.save(actual)
        );

    }


    /**
     * Elimina un profesional por su ID.
     *
     * @param idProfesional identificador del profesional
     * @return true si se eliminó, false si no existía
     */
    @Override
    public boolean eliminarProfesional(Long idProfesional) {

        if (profesionalRepository.existsById(idProfesional)) {

            profesionalRepository.deleteById(idProfesional);

            return true;

        }

        return false;

    }

}