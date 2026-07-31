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

// Importa la entidad Usuario
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

// Importa el servicio de usuarios
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.UsuarioService;


/**
 * Controlador REST para gestionar los usuarios
 * del Sistema de Gestión de Citas Odontológicas.
 *
 * Ruta base:
 *
 * /api/usuarios
 *
 * Este controlador permite:
 *
 * - Consultar usuarios.
 * - Consultar un usuario por ID.
 * - Registrar usuarios.
 * - Actualizar usuarios.
 * - Eliminar usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {


    /*
     * ============================================================
     * SERVICIO
     * ============================================================
     */

    /**
     * Servicio encargado de la lógica de usuarios.
     */
    private final UsuarioService usuarioService;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param usuarioService servicio de usuarios
     */
    public UsuarioController(
            UsuarioService usuarioService) {

        this.usuarioService = usuarioService;

    }


    /*
     * ============================================================
     * LISTAR USUARIOS
     * ============================================================
     */

    /**
     * Consulta todos los usuarios registrados.
     *
     * GET /api/usuarios
     *
     * @return lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        return ResponseEntity.ok(
            usuarioService.listarUsuarios()
        );

    }


    /*
     * ============================================================
     * BUSCAR USUARIO POR ID
     * ============================================================
     */

    /**
     * Busca un usuario por su identificador.
     *
     * GET /api/usuarios/{idUsuario}
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado o 404
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(
            @PathVariable Integer idUsuario) {

        Optional<Usuario> usuario =
            usuarioService.buscarUsuarioPorId(idUsuario);


        /*
         * Si existe, devuelve 200 OK.
         *
         * Si no existe, devuelve 404 NOT FOUND.
         */
        return usuario
            .map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity.notFound().build()
            );

    }


    /*
     * ============================================================
     * CREAR USUARIO
     * ============================================================
     */

    /**
     * Registra un nuevo usuario.
     *
     * POST /api/usuarios
     *
     * La contraseña recibida será convertida a BCrypt
     * por UsuarioServiceImpl.
     *
     * @param usuario datos del usuario
     * @return usuario creado
     */
    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(
            @Valid @RequestBody Usuario usuario) {

        Usuario usuarioGuardado =
            usuarioService.guardarUsuario(usuario);


        return ResponseEntity.ok(
            usuarioGuardado
        );

    }


    /*
     * ============================================================
     * ACTUALIZAR USUARIO
     * ============================================================
     */

    /**
     * Actualiza un usuario existente.
     *
     * PUT /api/usuarios/{idUsuario}
     *
     * @param idUsuario identificador del usuario
     * @param usuario nuevos datos
     * @return usuario actualizado o 404
     */
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> actualizarUsuario(

            @PathVariable Integer idUsuario,

            @Valid @RequestBody Usuario usuario) {


        /*
         * El servicio devuelve Optional<Usuario>.
         *
         * Si el usuario existe:
         *
         *     200 OK
         *
         * Si no existe:
         *
         *     404 NOT FOUND
         */
        Optional<Usuario> usuarioActualizado =
            usuarioService.actualizarUsuario(
                idUsuario,
                usuario
            );


        return usuarioActualizado
            .map(ResponseEntity::ok)
            .orElseGet(
                () -> ResponseEntity.notFound().build()
            );

    }


    /*
     * ============================================================
     * ELIMINAR USUARIO
     * ============================================================
     */

    /**
     * Elimina un usuario por su ID.
     *
     * DELETE /api/usuarios/{idUsuario}
     *
     * @param idUsuario identificador del usuario
     * @return 204 si se eliminó o 404 si no existe
     */
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Integer idUsuario) {


        boolean eliminado =
            usuarioService.eliminarUsuario(idUsuario);


        /*
         * Si se eliminó correctamente:
         *
         * 204 NO CONTENT
         */
        if (eliminado) {

            return ResponseEntity.noContent().build();

        }


        /*
         * Si no existe:
         *
         * 404 NOT FOUND
         */
        return ResponseEntity.notFound().build();

    }

}