package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

// Importa List y Optional para manejar colecciones y resultados opcionales
import java.util.List;
import java.util.Optional;

// Importa ResponseEntity para controlar las respuestas HTTP (código de estado y cuerpo)
import org.springframework.http.ResponseEntity;

// Importa NonNull para indicar que ciertos parámetros no deben ser nulos
import org.springframework.lang.NonNull;

// Importa Validated para activar validaciones a nivel de controlador
import org.springframework.validation.annotation.Validated;

// Importa las anotaciones REST de Spring Boot
import org.springframework.web.bind.annotation.*;

// Importa la entidad Paciente
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

// Importa el servicio de pacientes
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.PacienteService;

// Importa Valid para validar el cuerpo de las peticiones HTTP
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar los Pacientes del sistema.
 * 
 * Proporciona endpoints para crear, leer, actualizar y eliminar pacientes.
 * Ruta base: /api/pacientes
 */
@RestController
@RequestMapping("/api/pacientes")
@Validated
public class PacienteController {

    /**
     * Servicio de pacientes.
     * Se inyecta automáticamente mediante constructor.
     */
    private final PacienteService pacienteService;

    /**
     * Constructor para inyección de dependencias.
     * Spring Boot inyecta automáticamente el servicio.
     */
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * Lista todos los pacientes registrados en el sistema.
     * 
     * GET http://localhost:8765/api/pacientes
     * 
     * @return Lista de todos los pacientes
     */
    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    /**
     * Busca un paciente por su ID.
     * 
     * GET http://localhost:8765/api/pacientes/1
     * 
     * @param idPaciente ID del paciente a buscar
     * @return ResponseEntity con el paciente si existe o 404 Not Found
     */
    @GetMapping("/{idPaciente}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable @NonNull Long idPaciente) {

        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(idPaciente);

        // Si se encuentra el paciente se retorna con estado 200 OK,
        // de lo contrario se retorna 404 Not Found
        return paciente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea y guarda un nuevo paciente.
     * 
     * POST http://localhost:8765/api/pacientes
     * 
     * @param paciente Objeto Paciente con los datos a registrar (validado)
     * @return El paciente creado con su ID generado
     */
    @PostMapping
    public Paciente guardarPaciente(@Valid @RequestBody Paciente paciente) {
        return pacienteService.guardarPaciente(paciente);
    }

    /**
     * Actualiza un paciente existente.
     * 
     * PUT http://localhost:8765/api/pacientes/1
     * 
     * @param idPaciente ID del paciente a actualizar
     * @param paciente Nuevos datos del paciente (validado)
     * @return ResponseEntity con el paciente actualizado o 404 si no existe
     */
    @PutMapping("/{idPaciente}")
    public ResponseEntity<Paciente> actualizarPaciente(
            @PathVariable @NonNull Long idPaciente,
            @Valid @RequestBody Paciente paciente) {

        Optional<Paciente> pacienteExistente = pacienteService.buscarPacientePorId(idPaciente);

        if (pacienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Asigna el ID para asegurar que se actualice el registro correcto
        paciente.setIdPaciente(idPaciente);

        return ResponseEntity.ok(
                pacienteService.guardarPaciente(paciente));
    }

    /**
     * Elimina un paciente por su ID.
     * 
     * DELETE http://localhost:8765/api/pacientes/1
     * 
     * @param idPaciente ID del paciente a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 si no existe
     */
    @DeleteMapping("/{idPaciente}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable @NonNull Long idPaciente) {

        boolean eliminado = pacienteService.eliminarPaciente(idPaciente);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}