package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa List y Optional para manejar colecciones y resultados opcionales
import java.util.List;
import java.util.Optional;

// Importa Service para marcar esta clase como componente de servicio en Spring
import org.springframework.stereotype.Service;

// Importa la entidad Paciente
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

// Importa el repositorio para acceder a los datos
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;

// Importa NonNull para indicar que ciertos parámetros no deben ser nulos
import org.springframework.lang.NonNull;

/**
 * Servicio que contiene la lógica de negocio relacionada con los Pacientes.
 * 
 * Actúa como intermediario entre el Controller y el Repository.
 * Aquí se pueden agregar reglas de negocio adicionales en el futuro.
 */
@Service
public class PacienteService {

    /**
     * Repositorio de pacientes.
     * Se inyecta automáticamente mediante constructor.
     */
    private final PacienteRepository pacienteRepository;

    /**
     * Constructor para inyección de dependencias.
     * Spring inyecta automáticamente el repositorio.
     */
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * Lista todos los pacientes registrados en el sistema.
     * 
     * @return Lista de objetos Paciente
     */
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    /**
     * Busca un paciente por su ID.
     * 
     * @param idPaciente ID del paciente a buscar (no puede ser nulo)
     * @return Optional con el paciente si existe, o vacío si no se encuentra
     */
    public Optional<Paciente> buscarPacientePorId(@NonNull Long idPaciente) {
        return pacienteRepository.findById(idPaciente);
    }

    /**
     * Guarda un nuevo paciente o actualiza uno existente.
     * 
     * @param paciente Objeto Paciente a guardar (no puede ser nulo)
     * @return El paciente guardado con su ID generado
     */
    public Paciente guardarPaciente(@NonNull Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    /**
     * Elimina un paciente por su ID.
     * 
     * @param idPaciente ID del paciente a eliminar (no puede ser nulo)
     * @return true si el paciente fue eliminado, false si no existía
     */
    public boolean eliminarPaciente(@NonNull Long idPaciente) {

        // Verifica si el paciente existe antes de intentar eliminarlo
        if (pacienteRepository.existsById(idPaciente)) {
            pacienteRepository.deleteById(idPaciente);
            return true;
        }

        return false;
    }

}