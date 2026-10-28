/*
 * ============================================================
 * JWT AUTHENTICATION FILTER
 * ============================================================
 *
 * Sistema de Gestión de Citas Odontológicas
 * Consultorio Odontológico Dra. Magda Ortiz
 *
 * Este filtro intercepta las solicitudes HTTP antes de que
 * lleguen a los controladores de la aplicación.
 *
 * Su responsabilidad principal es:
 *
 * 1. Obtener el encabezado Authorization.
 * 2. Verificar que contenga un token Bearer.
 * 3. Extraer el JWT.
 * 4. Obtener el username almacenado en el token.
 * 5. Buscar el usuario en la base de datos.
 * 6. Validar la vigencia y autenticidad del JWT.
 * 7. Crear la autoridad correspondiente al rol del usuario.
 * 8. Registrar la autenticación en Spring Security.
 *
 * Formato esperado del encabezado:
 *
 * Authorization: Bearer TOKEN
 *
 * Ejemplo de autoridad:
 *
 * Rol almacenado:
 * Admin
 *
 * Autoridad generada:
 * ROLE_Admin
 *
 * El filtro no reemplaza las reglas definidas en
 * SecurityConfig. Su función es establecer la identidad
 * autenticada para que posteriormente Spring Security
 * pueda aplicar las reglas de autorización correspondientes.
 *
 * ============================================================
 */

package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Filtro encargado de procesar y validar la autenticación
 * mediante tokens JWT.
 *
 * OncePerRequestFilter garantiza que el filtro se ejecute
 * una sola vez por solicitud HTTP.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /*
     * ========================================================
     * SERVICIOS UTILIZADOS
     * ========================================================
     */

    /**
     * Servicio encargado de generar, extraer y validar
     * información relacionada con los tokens JWT.
     */
    private final JwtService jwtService;

    /**
     * Repositorio utilizado para consultar el usuario
     * asociado al username contenido en el JWT.
     */
    private final UsuarioRepository usuarioRepository;


    /*
     * ========================================================
     * CONSTRUCTOR
     * ========================================================
     *
     * Spring realiza la inyección de las dependencias
     * necesarias para procesar la autenticación.
     */

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioRepository usuarioRepository
    ) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }


    /*
     * ========================================================
     * PROCESAMIENTO DE PETICIONES
     * ========================================================
     *
     * Este método se ejecuta antes de que la solicitud llegue
     * al controlador correspondiente.
     */

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        /*
         * ====================================================
         * OBTENER ENCABEZADO DE AUTORIZACIÓN
         * ====================================================
         *
         * El cliente debe enviar el JWT mediante:
         *
         * Authorization: Bearer TOKEN
         */

        String authorizationHeader =
                request.getHeader("Authorization");


        /*
         * ====================================================
         * VERIFICAR EXISTENCIA DEL TOKEN
         * ====================================================
         *
         * Si no existe el encabezado Authorization o no utiliza
         * el esquema Bearer, el filtro no intenta autenticar
         * al usuario.
         *
         * La solicitud continúa y será Spring Security quien
         * determine posteriormente si el recurso solicitado
         * requiere autenticación.
         */

        if (
                authorizationHeader == null
                        ||
                !authorizationHeader.startsWith("Bearer ")
        ) {

            System.out.println(
                    "[JWT DEBUG] Sin header Authorization válido para: "
                            + request.getMethod()
                            + " "
                            + request.getRequestURI()
            );

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }


        /*
         * ====================================================
         * EXTRAER TOKEN JWT
         * ====================================================
         *
         * Se elimina el prefijo "Bearer " y se conserva
         * únicamente el JWT.
         */

        String token =
                authorizationHeader.substring(7);


        /*
         * ====================================================
         * VALIDACIÓN DEL TOKEN
         * ====================================================
         */

        try {

            /*
             * =================================================
             * OBTENER USERNAME DEL JWT
             * =================================================
             *
             * El username corresponde al subject utilizado
             * durante la generación del token.
             */

            String username =
                    jwtService.obtenerUsername(token);


            System.out.println(
                    "[JWT DEBUG] Username extraído del token: "
                            + username
            );


            /*
             * =================================================
             * VERIFICAR IDENTIDAD
             * =================================================
             *
             * Solo se continúa con la autenticación si:
             *
             * - El JWT contiene un username.
             * - El SecurityContext todavía no contiene una
             *   autenticación.
             */

            if (
                    username != null
                            &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null
            ) {

                /*
                 * =================================================
                 * BUSCAR USUARIO EN LA BASE DE DATOS
                 * =================================================
                 *
                 * El usuario debe existir para que el JWT pueda
                 * establecer una autenticación válida.
                 */

                Usuario usuario =
                        usuarioRepository
                                .findByUsername(username)
                                .orElse(null);


                System.out.println(
                        "[JWT DEBUG] Usuario encontrado en BD: "
                                + (usuario != null)
                );


                /*
                 * =================================================
                 * VALIDAR USUARIO Y TOKEN
                 * =================================================
                 *
                 * El token solamente se considera válido cuando:
                 *
                 * 1. El usuario existe.
                 * 2. El JWT supera la validación implementada
                 *    por JwtService.
                 */

                boolean tokenValido =
                        usuario != null
                                &&
                        jwtService.validarToken(
                                token,
                                username
                        );


                System.out.println(
                        "[JWT DEBUG] Token válido: "
                                + tokenValido
                );


                if (tokenValido) {

                    /*
                     * =============================================
                     * CREAR AUTORIDAD DEL USUARIO
                     * =============================================
                     *
                     * El rol almacenado en la base de datos se
                     * transforma al formato utilizado por
                     * Spring Security.
                     *
                     * Ejemplo:
                     *
                     * Admin
                     *      ↓
                     * ROLE_Admin
                     */

                    SimpleGrantedAuthority autoridad =
                            new SimpleGrantedAuthority(
                                    "ROLE_"
                                            +
                                    usuario.getRol()
                            );


                    /*
                     * =============================================
                     * CREAR OBJETO DE AUTENTICACIÓN
                     * =============================================
                     *
                     * UsernamePasswordAuthenticationToken
                     * representa al usuario autenticado dentro
                     * del contexto de seguridad de Spring.
                     *
                     * La contraseña no se almacena dentro del
                     * objeto de autenticación.
                     */

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    usuario.getUsername(),
                                    null,
                                    List.of(autoridad)
                            );


                    /*
                     * =============================================
                     * REGISTRAR AUTENTICACIÓN
                     * =============================================
                     *
                     * El SecurityContextHolder mantiene la
                     * identidad autenticada durante el procesamiento
                     * de la solicitud.
                     *
                     * A partir de este punto, los mecanismos de
                     * autorización de Spring Security pueden
                     * consultar el usuario y sus autoridades.
                     */

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }
            }

        } catch (Exception exception) {

            /*
             * ====================================================
             * MANEJO CONTROLADO DE ERRORES JWT
             * ====================================================
             *
             * Si el token está mal formado, expirado, no puede
             * validarse o se produce cualquier otro problema
             * durante su procesamiento, no se establece una
             * autenticación inválida.
             *
             * La solicitud continúa hacia la cadena de filtros.
             * Spring Security determinará posteriormente si el
             * recurso requiere una autenticación válida.
             *
             * No se exponen detalles del token ni información
             * sensible en la respuesta al cliente.
             */

            System.out.println(
                    "[JWT DEBUG] Excepción al procesar el token: "
                            + exception.getClass().getSimpleName()
                            + " -> "
                            + exception.getMessage()
            );

        }


        /*
         * ====================================================
         * CONTINUAR CADENA DE FILTROS
         * ====================================================
         *
         * Una vez procesado el JWT, la solicitud continúa hacia
         * los siguientes filtros y posteriormente hacia el
         * controlador correspondiente.
         */

        filterChain.doFilter(
                request,
                response
        );
    }
}