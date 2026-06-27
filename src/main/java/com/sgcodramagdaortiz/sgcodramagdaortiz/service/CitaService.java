package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa List y Optional para manejar colecciones y resultados opcionales
import java.util.List;
import java.util.Optional;

// Importa NonNull para indicar que ciertos parámetros y retornos no deben ser nulos
import org.springframework.lang.NonNull;

// Importa Service para marcar esta clase como componente de servicio en Spring Boot
import org.springframework.stereotype.Service;

// Importa la entidad Cita
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

// Importa el repositorio para acceder a los datos de citas
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;

/**
 * Servicio que contiene la lógica de negocio relacionada con las Citas.
 * 
 * Actúa como intermediario entre el CitaController y el CitaRepository.
 * Aquí se pueden agregar reglas de negocio adicionales (validaciones, cálculos, etc.).
 */
@Service
public class CitaService {

    /**
     * Repositorio de citas.
     * Se inyecta automáticamente mediante constructor.
     */
    private final CitaRepository citaRepository;

    /**
     * Constructor para inyección de dependencias.
     * Spring Boot inyecta automáticamente el repositorio.
     */
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    /**
     * Lista todas las citas registradas en el sistema.
     * 
     * @return Lista de objetos Cita
     */
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    /**
     * Busca una cita por su ID.
     * 
     * @param idCita ID de la cita a buscar (no puede ser nulo)
     * @return Optional con la cita si existe, o vacío si no se encuentra
     */
    public Optional<Cita> buscarCitaPorId(@NonNull Long idCita) {
        return citaRepository.findById(idCita);
    }

    /**
     * Guarda una nueva cita o actualiza una existente.
     * 
     * @param cita Objeto Cita a guardar (no puede ser nulo)
     * @return La cita guardada con su ID generado o actualizado (nunca nulo)
     */
    @NonNull
    public Cita guardarCita(@NonNull Cita cita) {
        return citaRepository.save(cita);
    }

    /**
     * Elimina una cita por su ID.
     * 
     * @param idCita ID de la cita a eliminar (no puede ser nulo)
     * @return true si la cita fue eliminada exitosamente, false si no existía
     */
    public boolean eliminarCita(@NonNull Long idCita) {

        // Verifica si la cita existe antes de intentar eliminarla
        if (citaRepository.existsById(idCita)) {
            citaRepository.deleteById(idCita);
            return true;
        }

        return false;
    }

}