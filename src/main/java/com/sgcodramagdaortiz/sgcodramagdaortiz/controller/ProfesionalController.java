package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

// Importa List y Optional
import java.util.List;
import java.util.Optional;

// Importa ResponseEntity para controlar las respuestas HTTP
import org.springframework.http.ResponseEntity;

// Importa las anotaciones REST de Spring Boot
import org.springframework.web.bind.annotation.*;

// Importa Valid para validar los datos recibidos
import jakarta.validation.Valid;

// Importa la entidad Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

// Importa el servicio de profesionales
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.ProfesionalService;

/**
 * ============================================================
 * PROFESIONALCONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST para gestionar los profesionales
 * del Sistema de Gestión de Citas Odontológicas.
 *
 * Ruta base:
 *
 * /api/profesionales
 *
 * Este controlador permite:
 *
 * - Consultar profesionales.
 * - Consultar un profesional por ID.
 * - Registrar profesionales.
 * - Actualizar profesionales.
 * - Eliminar profesionales.
 */
@RestController
@RequestMapping("/api/profesionales")
@CrossOrigin(origins = "http://localhost:5173")
public class ProfesionalController {


    /**
     * Servicio encargado de la lógica de profesionales.
     */
    private final ProfesionalService profesionalService;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param profesionalService servicio de profesionales
     */
    public ProfesionalController(ProfesionalService profesionalService) {

        this.profesionalService = profesionalService;

    }


    /**
     * Consulta todos los profesionales registrados.
     *
     * GET /api/profesionales
     *
     * @return lista de profesionales
     */
    @GetMapping
    public ResponseEntity<List<Profesional>> listarProfesionales() {

        return ResponseEntity.ok(
            profesionalService.listarProfesionales()
        );

    }


    /**
     * Busca un profesional por su identificador.
     *
     * GET /api/profesionales/{idProfesional}
     *
     * @param idProfesional identificador del profesional
     * @return profesional encontrado o 404
     */
    @GetMapping("/{idProfesional}")
    public ResponseEntity<Profesional> buscarProfesionalPorId(
            @PathVariable Long idProfesional) {

        Optional<Profesional> profesional =
            profesionalService.buscarProfesionalPorId(idProfesional);

        return profesional
            .map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity.notFound().build()
            );

    }


    /**
     * Registra un nuevo profesional.
     *
     * POST /api/profesionales
     *
     * @param profesional datos del profesional
     * @return profesional creado
     */
    @PostMapping
    public ResponseEntity<Profesional> guardarProfesional(
            @Valid @RequestBody Profesional profesional) {

        Profesional profesionalGuardado =
            profesionalService.guardarProfesional(profesional);

        return ResponseEntity.ok(
            profesionalGuardado
        );

    }


    /**
     * Actualiza un profesional existente.
     *
     * PUT /api/profesionales/{idProfesional}
     *
     * @param idProfesional identificador del profesional
     * @param profesional nuevos datos
     * @return profesional actualizado o 404
     */
    @PutMapping("/{idProfesional}")
    public ResponseEntity<Profesional> actualizarProfesional(

            @PathVariable Long idProfesional,

            @Valid @RequestBody Profesional profesional) {

        Optional<Profesional> profesionalActualizado =
            profesionalService.actualizarProfesional(
                idProfesional,
                profesional
            );

        return profesionalActualizado
            .map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity.notFound().build()
            );

    }


    /**
     * Elimina un profesional por su ID.
     *
     * DELETE /api/profesionales/{idProfesional}
     *
     * @param idProfesional identificador del profesional
     * @return 204 si se eliminó o 404 si no existe
     */
    @DeleteMapping("/{idProfesional}")
    public ResponseEntity<Void> eliminarProfesional(
            @PathVariable Long idProfesional) {

        boolean eliminado =
            profesionalService.eliminarProfesional(idProfesional);

        if (eliminado) {

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();

    }

}