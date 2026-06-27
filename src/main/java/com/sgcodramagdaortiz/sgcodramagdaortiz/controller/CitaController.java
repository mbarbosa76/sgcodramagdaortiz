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

// Importa la entidad Cita
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

// Importa el servicio de citas
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.CitaService;

// Importa Valid para validar el cuerpo de las peticiones HTTP
import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar las Citas del sistema.
 * 
 * Proporciona endpoints para crear, leer, actualizar y eliminar citas.
 * Ruta base: /api/citas
 */
@RestController
@RequestMapping("/api/citas")
@Validated
public class CitaController {

    /**
     * Servicio de citas.
     * Se inyecta automáticamente mediante constructor.
     */
    private final CitaService citaService;

    /**
     * Constructor para inyección de dependencias.
     * Spring Boot inyecta automáticamente el servicio.
     */
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    /**
     * Lista todas las citas registradas en el sistema.
     * 
     * GET http://localhost:8765/api/citas
     * 
     * @return Lista de todas las citas
     */
    @GetMapping
    public List<Cita> listarCitas() {
        return citaService.listarCitas();
    }

    /**
     * Busca una cita por su ID.
     * 
     * GET http://localhost:8765/api/citas/1
     * 
     * @param idCita ID de la cita a buscar
     * @return ResponseEntity con la cita si existe o 404 Not Found
     */
    @GetMapping("/{idCita}")
    public ResponseEntity<Cita> buscarCitaPorId(@PathVariable @NonNull Long idCita) {

        Optional<Cita> cita = citaService.buscarCitaPorId(idCita);

        // Si se encuentra la cita se retorna con estado 200 OK,
        // de lo contrario se retorna 404 Not Found
        return cita.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea y guarda una nueva cita.
     * 
     * POST http://localhost:8765/api/citas
     * 
     * @param cita Objeto Cita con los datos a registrar (validado)
     * @return La cita creada con su ID generado
     */
    @PostMapping
    public Cita guardarCita(@Valid @RequestBody Cita cita) {
        return citaService.guardarCita(cita);
    }

    /**
     * Actualiza una cita existente.
     * 
     * PUT http://localhost:8765/api/citas/1
     * 
     * @param idCita ID de la cita a actualizar
     * @param cita Nuevos datos de la cita (validado)
     * @return ResponseEntity con la cita actualizada o 404 si no existe
     */
    @PutMapping("/{idCita}")
    public ResponseEntity<Cita> actualizarCita(
            @PathVariable @NonNull Long idCita,
            @Valid @RequestBody Cita cita) {

        Optional<Cita> citaExistente = citaService.buscarCitaPorId(idCita);

        if (citaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Asigna el ID para asegurar que se actualice el registro correcto
        cita.setIdCita(idCita);

        return ResponseEntity.ok(
                citaService.guardarCita(cita));
    }

    /**
     * Elimina una cita por su ID.
     * 
     * DELETE http://localhost:8765/api/citas/1
     * 
     * @param idCita ID de la cita a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 si no existe
     */
    @DeleteMapping("/{idCita}")
    public ResponseEntity<Void> eliminarCita(@PathVariable @NonNull Long idCita) {

        boolean eliminado = citaService.eliminarCita(idCita);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}