package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa LocalDate para registrar la fecha de creación
import java.time.LocalDate;

// Importa List y Optional
import java.util.List;
import java.util.Optional;

// Importa anotación Service
import org.springframework.stereotype.Service;

// Importa PasswordEncoder para utilizar BCrypt
import org.springframework.security.crypto.password.PasswordEncoder;

// Importa la entidad Usuario
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

// Importa el repositorio de Usuario
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository;


/**
 * Implementación del servicio de usuarios.
 *
 * Contiene la lógica de negocio relacionada
 * con el registro, consulta, actualización
 * y eliminación de usuarios.
 *
 * También se encarga de convertir las contraseñas
 * en hashes BCrypt antes de almacenarlas.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Repositorio de usuarios.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Codificador BCrypt.
     */
    private final PasswordEncoder passwordEncoder;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param usuarioRepository repositorio de usuarios
     * @param passwordEncoder codificador BCrypt
     */
    public UsuarioServiceImpl(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Lista todos los usuarios.
     *
     * @return lista de usuarios
     */
    @Override
    public List<Usuario> listarUsuarios() {

        return usuarioRepository.findAll();

    }


    /**
     * Busca un usuario por su ID.
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado
     */
    @Override
    public Optional<Usuario> buscarUsuarioPorId(
            Integer idUsuario) {

        return usuarioRepository.findById(idUsuario);

    }


    /**
     * Busca un usuario mediante su nombre de usuario.
     *
     * Este método se utiliza principalmente
     * durante el proceso de autenticación.
     *
     * @param username nombre de usuario
     * @return usuario encontrado
     */
    @Override
    public Optional<Usuario> buscarPorUsername(
            String username) {

        return usuarioRepository.findByUsername(username);

    }


    /**
     * Registra un nuevo usuario.
     *
     * La contraseña recibida debe ser una contraseña
     * normal y aquí se convierte a BCrypt antes
     * de almacenarla.
     *
     * @param usuario usuario a registrar
     * @return usuario guardado
     */
    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        /*
         * Si el usuario no tiene fecha de creación,
         * se asigna automáticamente la fecha actual.
         */
        if (usuario.getFechaCreacion() == null) {

            usuario.setFechaCreacion(
                LocalDate.now()
            );

        }


        /*
         * La contraseña recibida desde el frontend
         * se convierte en un hash BCrypt.
         *
         * IMPORTANTE:
         * El frontend NO debe enviar un hash BCrypt.
         */
        if (usuario.getPasswordHash() != null
                && !usuario.getPasswordHash().isBlank()) {

            usuario.setPasswordHash(
                passwordEncoder.encode(
                    usuario.getPasswordHash()
                )
            );

        }


        /*
         * Guarda el usuario en MySQL.
         */
        return usuarioRepository.save(usuario);

    }


    /**
     * Actualiza un usuario existente.
     *
     * Si se recibe una contraseña nueva,
     * se genera un nuevo hash BCrypt.
     *
     * Si la contraseña viene vacía,
     * se conserva la contraseña existente.
     *
     * @param idUsuario identificador del usuario
     * @param usuario datos nuevos
     * @return usuario actualizado
     */
    @Override
    public Optional<Usuario> actualizarUsuario(
            Integer idUsuario,
            Usuario usuario) {

        /*
         * Busca primero el usuario existente.
         */
        Optional<Usuario> usuarioExistente =
            usuarioRepository.findById(idUsuario);


        if (usuarioExistente.isEmpty()) {

            return Optional.empty();

        }


        Usuario actual =
            usuarioExistente.get();


        /*
         * Actualiza los datos básicos.
         */
        actual.setUsername(
            usuario.getUsername()
        );

        actual.setNombre(
            usuario.getNombre()
        );

        actual.setApellido(
            usuario.getApellido()
        );

        actual.setCorreo(
            usuario.getCorreo()
        );

        actual.setTelefono(
            usuario.getTelefono()
        );

        actual.setRol(
            usuario.getRol()
        );


        /*
         * Solo cambia la contraseña si el usuario
         * introdujo una nueva.
         */
        if (usuario.getPasswordHash() != null
                && !usuario.getPasswordHash().isBlank()) {

            actual.setPasswordHash(
                passwordEncoder.encode(
                    usuario.getPasswordHash()
                )
            );

        }


        /*
         * Guarda los cambios.
         */
        return Optional.of(
            usuarioRepository.save(actual)
        );

    }


    /**
     * Elimina un usuario.
     *
     * @param idUsuario identificador del usuario
     * @return true si se eliminó
     */
    @Override
    public boolean eliminarUsuario(Integer idUsuario) {

        if (usuarioRepository.existsById(idUsuario)) {

            usuarioRepository.deleteById(idUsuario);

            return true;

        }

        return false;

    }

}