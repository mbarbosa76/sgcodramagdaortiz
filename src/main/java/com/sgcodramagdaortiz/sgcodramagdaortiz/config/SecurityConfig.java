package com.sgcodramagdaortiz.sgcodramagdaortiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.sgcodramagdaortiz.sgcodramagdaortiz.service.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Configuración principal de Spring Security.
 *
 * Define las reglas de seguridad de la API REST
 * y registra el filtro encargado de validar JWT.
 */
@Configuration
public class SecurityConfig {

    /**
     * Filtro encargado de procesar los tokens JWT.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param jwtAuthenticationFilter filtro JWT
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura la cadena de seguridad de Spring.
     *
     * @param http configuración HTTP de Spring Security
     * @return cadena de filtros configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            /*
             * Desactiva CSRF porque estamos trabajando
             * con una API REST que utiliza JWT.
             */
            .csrf(csrf -> csrf.disable())

            /*
             * Permite solicitudes provenientes del frontend
             * React/Vite.
             */
            .cors(cors -> {
            })

            /*
             * La aplicación no utiliza sesiones HTTP.
             *
             * Cada petición protegida debe enviar
             * su propio token JWT.
             */
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            /*
             * Configura la respuesta cuando un usuario
             * intenta acceder a un recurso protegido
             * sin estar autenticado.
             *
             * Se devuelve 401 Unauthorized en lugar
             * de la respuesta predeterminada 403 Forbidden.
             */
            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                    (request, response, authException) -> {

                        response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "No está autenticado. Debe proporcionar un token JWT."
                        );
                    }
                )
            )

            /*
             * Define las reglas de autorización.
             */
            .authorizeHttpRequests(auth -> auth

                /*
                 * Login público.
                 *
                 * No requiere JWT porque aquí se obtiene
                 * precisamente el token.
                 */
                .requestMatchers(
                    "/api/auth/login"
                ).permitAll()

                /*
                 * Permite las solicitudes OPTIONS utilizadas
                 * por CORS desde React.
                 */
                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                ).permitAll()

                /*
                 * ====================================================
                 * MODO DESARROLLO: SIN RESTRICCIONES
                 * ====================================================
                 *
                 * Mientras el proyecto está en desarrollo, se permite
                 * el acceso a TODOS los endpoints sin necesidad de
                 * token JWT (incluyendo /api/pacientes, /api/citas,
                 * /api/usuarios y /api/servicios).
                 *
                 * Esto permite, por ejemplo, abrir
                 * http://localhost:8765/api/pacientes directamente
                 * en el navegador para verificar los datos, sin
                 * tener que iniciar sesión primero.
                 *
                 * IMPORTANTE PARA MÁS ADELANTE:
                 *
                 * Antes de llevar este proyecto a producción (o de
                 * entregarlo si la seguridad hace parte de la
                 * evaluación), esta línea debe volver a ser:
                 *
                 *     .anyRequest().authenticated()
                 *
                 * De lo contrario, cualquier persona podría leer,
                 * crear, modificar o eliminar pacientes, citas y
                 * usuarios sin iniciar sesión.
                 */
                .anyRequest().permitAll()
            )

            /*
             * Agrega nuestro filtro JWT antes del filtro
             * estándar de autenticación.
             */
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    /**
     * Bean utilizado para cifrar y verificar contraseñas
     * utilizando BCrypt.
     *
     * @return PasswordEncoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}