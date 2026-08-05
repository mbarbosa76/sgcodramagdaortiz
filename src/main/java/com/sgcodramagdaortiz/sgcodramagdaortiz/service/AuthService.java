package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.LoginRequest;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.LoginResponse;
import com.sgcodramagdaortiz.sgcodramagdaortiz.exception.AutenticacionException;
import com.sgcodramagdaortiz.sgcodramagdaortiz.exception.RecursoNoEncontradoException;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

/**
 * Servicio encargado de realizar la autenticación
 * de los usuarios del sistema.
 */
@Service
public class AuthService {

    /**
     * Servicio de usuarios.
     */
    private final UsuarioService usuarioService;

    /**
     * Codificador utilizado para verificar
     * las contraseñas almacenadas mediante BCrypt.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Servicio encargado de generar los tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Constructor para inyección de dependencias.
     */
    public AuthService(
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Realiza el proceso de autenticación.
     *
     * 1. Busca el usuario.
     * 2. Comprueba la contraseña.
     * 3. Genera el token JWT.
     * 4. Devuelve la información necesaria al frontend.
     *
     * @param request datos enviados desde el login
     * @return respuesta con JWT y datos del usuario
     */
    public LoginResponse autenticar(LoginRequest request) {

        Usuario usuario = usuarioService
                .buscarPorUsername(request.getUsername())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "Usuario o contraseña incorrectos"
                        )
                );

        /*
         * Compara la contraseña enviada con el hash
         * almacenado en la base de datos.
         */
        boolean passwordCorrecta =
                passwordEncoder.matches(
                        request.getPassword(),
                        usuario.getPasswordHash()
                );

        /*
         * Si la contraseña no coincide,
         * se devuelve el mismo mensaje genérico.
         */
        if (!passwordCorrecta) {

throw new AutenticacionException(
    "Usuario o contraseña incorrectos"
);
        }

        /*
         * Genera el token JWT después de autenticar
         * correctamente al usuario.
         */
        String token =
                jwtService.generarToken(usuario);

        /*
         * Devuelve solamente los datos necesarios.
         *
         * La contraseña o passwordHash nunca se devuelve.
         */
        return new LoginResponse(
                token,
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getRol()
        );
    }
}