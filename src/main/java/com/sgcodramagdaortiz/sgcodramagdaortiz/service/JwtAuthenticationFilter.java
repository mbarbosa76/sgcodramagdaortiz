package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ============================================================
 * JWT AUTHENTICATION FILTER
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Filtro encargado de interceptar las peticiones HTTP
 * y comprobar si contienen un token JWT válido.
 *
 * El token debe enviarse mediante:
 *
 * Authorization: Bearer TOKEN
 *
 * IMPORTANTE:
 * Este filtro utiliza directamente el repositorio de usuarios
 * y NO UsuarioService.
 *
 * Esto evita crear una dependencia circular entre:
 *
 * JwtAuthenticationFilter
 * UsuarioServiceImpl
 * SecurityConfig
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Servicio encargado de validar y leer los tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Repositorio encargado de consultar directamente
     * los usuarios en la base de datos.
     */
    private final com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository usuarioRepository;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param jwtService servicio encargado de trabajar con JWT
     * @param usuarioRepository repositorio de usuarios
     */
    public JwtAuthenticationFilter(
            JwtService jwtService,
            com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository usuarioRepository) {

        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }


    /**
     * ========================================================
     * PROCESAMIENTO DE CADA PETICIÓN
     * ========================================================
     *
     * Intercepta cada petición HTTP antes de que llegue
     * al controlador correspondiente.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {


        /*
         * Obtiene el encabezado Authorization.
         *
         * Ejemplo:
         *
         * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
         */
        String authorizationHeader =
                request.getHeader("Authorization");


        /*
         * Si no existe el encabezado o no comienza
         * con "Bearer ", se continúa normalmente.
         *
         * Spring Security posteriormente determinará
         * si el endpoint requiere autenticación.
         */
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);

            return;
        }


        /*
         * Extrae únicamente el token JWT.
         *
         * Se eliminan los primeros 7 caracteres:
         *
         * "Bearer "
         */
        String token =
                authorizationHeader.substring(7);


        try {

            /*
             * Obtiene el username almacenado
             * dentro del token JWT.
             */
            String username =
                    jwtService.obtenerUsername(token);


            /*
             * Comprueba que:
             *
             * 1. El username exista.
             * 2. Todavía no exista una autenticación
             *    registrada para esta petición.
             */
            if (username != null
                    && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {


                /*
                 * Busca directamente el usuario en el repositorio.
                 *
                 * IMPORTANTE:
                 * Ya NO utilizamos UsuarioService aquí.
                 *
                 * Esto elimina la dependencia circular.
                 */
                Usuario usuario =
                        usuarioRepository
                            .findByUsername(username)
                            .orElse(null);


                /*
                 * Verifica que el usuario exista y que
                 * el token JWT sea válido.
                 */
                if (usuario != null
                        && jwtService.validarToken(
                                token,
                                username)) {


                    /*
                     * Convierte el rol almacenado en MySQL
                     * en una autoridad de Spring Security.
                     *
                     * Ejemplos:
                     *
                     * Admin
                     * Recepcionista
                     * Profesional
                     */
                    SimpleGrantedAuthority autoridad =
                            new SimpleGrantedAuthority(
                                    "ROLE_" + usuario.getRol()
                            );


                    /*
                     * Crea la autenticación de Spring Security.
                     */
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    usuario.getUsername(),
                                    null,
                                    java.util.List.of(autoridad)
                            );


                    /*
                     * Registra la autenticación dentro
                     * del contexto de seguridad.
                     */
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }


        } catch (Exception exception) {

            /*
             * Si el token es inválido, está alterado
             * o está expirado, no se establece autenticación.
             *
             * Spring Security decidirá posteriormente
             * si debe responder con 401 Unauthorized.
             */
        }


        /*
         * Continúa con el resto de filtros.
         */
        filterChain.doFilter(request, response);
    }
}