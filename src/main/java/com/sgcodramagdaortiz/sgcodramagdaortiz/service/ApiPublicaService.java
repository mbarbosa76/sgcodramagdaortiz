package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa List para manejar las colecciones que devuelve este servicio
import java.util.List;

// Importa Service para marcar esta clase como componente
// de servicio en Spring (para que Spring la administre
// automáticamente mediante inyección de dependencias)
import org.springframework.stereotype.Service;

// Importa las entidades que consulta este servicio
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

// Importa los repositorios para acceder a los datos
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;

/**
 * ============================================================
 * APIPUBLICASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Servicio que contiene la lógica de negocio de la
 * "API pública" del proyecto.
 *
 * Esta clase actúa como intermediaria entre
 * ApiPublicaController y los repositorios (UsuarioRepository,
 * PacienteRepository y CitaRepository), siguiendo el mismo
 * patrón de capas (Controller -> Service -> Repository) que
 * se usa en el resto del proyecto (por ejemplo, en
 * PacienteService y UsuarioService).
 *
 * PROPÓSITO:
 *
 * Se creó para poder hacer pruebas de consulta con Postman
 * mediante los siguientes endpoints:
 *
 * GET http://localhost:8765/api/publica/usuarios
 * GET http://localhost:8765/api/publica/pacientes
 * GET http://localhost:8765/api/publica/citas
 *
 * IMPORTANTE - NOTA DE SEGURIDAD:
 *
 * Actualmente SecurityConfig permite el acceso a todos los
 * endpoints sin token (.anyRequest().permitAll()), por lo
 * que esta API "pública" no está realmente más abierta que
 * el resto del proyecto en este momento.
 *
 * Además, como listarUsuariosPublico() devuelve el objeto
 * Usuario completo, la respuesta JSON incluirá también el
 * campo "passwordHash" (la contraseña cifrada). Está bien
 * para hacer pruebas en Postman durante el desarrollo, pero
 * antes de usar este endpoint en un entorno real conviene
 * devolver un DTO sin ese campo, para no exponer las
 * contraseñas cifradas de los usuarios.
 */
@Service
public class ApiPublicaService {

    /**
     * Repositorio de usuarios.
     * Se inyecta automáticamente mediante constructor.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Repositorio de pacientes.
     * Se inyecta automáticamente mediante constructor.
     */
    private final PacienteRepository pacienteRepository;

    /**
     * Repositorio de citas.
     * Se inyecta automáticamente mediante constructor.
     */
    private final CitaRepository citaRepository;

    /**
     * Constructor para inyección de dependencias.
     * Spring inyecta automáticamente los tres repositorios.
     *
     * @param usuarioRepository  repositorio de usuarios
     * @param pacienteRepository repositorio de pacientes
     * @param citaRepository     repositorio de citas
     */
    public ApiPublicaService(
            UsuarioRepository usuarioRepository,
            PacienteRepository pacienteRepository,
            CitaRepository citaRepository) {

        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
    }

    /**
     * Consulta todos los usuarios registrados en el sistema.
     *
     * Utilizado por el endpoint público:
     *
     * GET /api/publica/usuarios
     *
     * @return lista completa de usuarios
     */
    public List<Usuario> listarUsuariosPublico() {
        return usuarioRepository.findAll();
    }

    /**
     * Consulta todos los pacientes registrados en el sistema.
     *
     * Utilizado por el endpoint público:
     *
     * GET /api/publica/pacientes
     *
     * @return lista completa de pacientes
     */
    public List<Paciente> listarPacientesPublico() {
        return pacienteRepository.findAll();
    }

    /**
     * Consulta todas las citas registradas en el sistema.
     *
     * Utilizado por el endpoint público:
     *
     * GET /api/publica/citas
     *
     * @return lista completa de citas
     */
    public List<Cita> listarCitasPublico() {
        return citaRepository.findAll();
    }

}