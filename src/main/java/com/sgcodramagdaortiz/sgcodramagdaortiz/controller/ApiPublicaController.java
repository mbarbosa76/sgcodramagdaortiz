package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

// Importa List para manejar las colecciones que devuelve este controlador
import java.util.List;

// Importa ResponseEntity para controlar la respuesta HTTP
// (código de estado y cuerpo de la respuesta)
import org.springframework.http.ResponseEntity;

// Importa las anotaciones REST de Spring Boot
// (@RestController, @RequestMapping, @GetMapping, @CrossOrigin, etc.)
import org.springframework.web.bind.annotation.*;

// Importa las entidades que expone este controlador
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

// Importa el servicio encargado de la lógica de la API pública
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.ApiPublicaService;

/**
 * ============================================================
 * APIPUBLICACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST que expone la "API pública" del proyecto.
 *
 * Se creó específicamente para poder hacer pruebas de
 * consulta con Postman, usando el método GET, sobre las
 * siguientes URL:
 *
 * GET http://localhost:8765/api/publica/usuarios
 * GET http://localhost:8765/api/publica/pacientes
 * GET http://localhost:8765/api/publica/citas
 *
 * Ruta base: /api/publica
 *
 * Sigue el mismo patrón de capas que el resto del proyecto:
 *
 * ApiPublicaController -> ApiPublicaService -> Repository -> MySQL
 */
@RestController
@RequestMapping("/api/publica")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiPublicaController {

    /**
     * Servicio encargado de la lógica de la API pública.
     * Se inyecta automáticamente mediante constructor.
     */
    private final ApiPublicaService apiPublicaService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param apiPublicaService servicio de la API pública
     */
    public ApiPublicaController(ApiPublicaService apiPublicaService) {
        this.apiPublicaService = apiPublicaService;
    }

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * GET http://localhost:8765/api/publica/usuarios
     *
     * Pensado para probarse directamente desde Postman,
     * sin necesidad de enviar ningún encabezado adicional
     * (mientras SecurityConfig tenga .anyRequest().permitAll()).
     *
     * @return 200 OK con la lista de usuarios en el cuerpo
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuariosPublico() {

        return ResponseEntity.ok(
            apiPublicaService.listarUsuariosPublico()
        );

    }

    /**
     * Lista todos los pacientes registrados en el sistema.
     *
     * GET http://localhost:8765/api/publica/pacientes
     *
     * Pensado para probarse directamente desde Postman,
     * sin necesidad de enviar ningún encabezado adicional
     * (mientras SecurityConfig tenga .anyRequest().permitAll()).
     *
     * @return 200 OK con la lista de pacientes en el cuerpo
     */
    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> listarPacientesPublico() {

        return ResponseEntity.ok(
            apiPublicaService.listarPacientesPublico()
        );

    }

    /**
     * Lista todas las citas registradas en el sistema.
     *
     * GET http://localhost:8765/api/publica/citas
     *
     * Pensado para probarse directamente desde Postman,
     * sin necesidad de enviar ningún encabezado adicional
     * (mientras SecurityConfig tenga .anyRequest().permitAll()).
     *
     * @return 200 OK con la lista de citas en el cuerpo
     */
    @GetMapping("/citas")
    public ResponseEntity<List<Cita>> listarCitasPublico() {

        return ResponseEntity.ok(
            apiPublicaService.listarCitasPublico()
        );

    }

}