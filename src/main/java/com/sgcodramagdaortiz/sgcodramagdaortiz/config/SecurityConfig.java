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
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura la cadena de seguridad de Spring.
     *
     * @param http configuración HTTP de Spring Security
     * @return cadena de filtros configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            /*
             * Desactiva CSRF porque estamos trabajando
             * con una API REST que utiliza JWT.
             */
            .csrf(csrf -> csrf.disable())

            /*
             * Permite solicitudes provenientes del frontend (CORS).
             */
            .cors(cors -> {
            })

            /*
             * La aplicación no utiliza sesiones HTTP (Stateful).
             * Cada petición protegida debe enviar su propio token JWT.
             */
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

/*
============================================================
MANEJO DE ERRORES DE SEGURIDAD

401:
Usuario no autenticado.

403:
Usuario autenticado pero sin permisos.

============================================================
*/

/*
============================================================
MANEJO DE ERRORES DE SEGURIDAD

401:
No existe autenticación válida.

403:
Existe autenticación pero el rol no permite
acceder al recurso.

============================================================
*/

.exceptionHandling(exception -> {


    exception.authenticationEntryPoint(

        (request, response, authException) -> {


            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );


            response.getWriter().write(
                "No autenticado"
            );


        }


    );


    exception.accessDeniedHandler(

        (request, response, accessDeniedException) -> {


            response.setStatus(
                    HttpServletResponse.SC_FORBIDDEN
            );


            response.getWriter().write(
                "No tiene permisos para acceder a este recurso"
            );


        }


    );


})

            /*
             * REGLAS DE AUTORIZACIÓN POR ROL Y ENDPOINTS
             */
            .authorizeHttpRequests(auth -> auth

                /*
                 * LOGIN PÚBLICO & API COLOMBIA
                 */
                .requestMatchers("/api/auth/login").permitAll()

                /*
                 * CORS PRE-FLIGHT (OPTIONS)
                 */
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

/*
 * ============================================================
 * DASHBOARD ADMIN
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/admin"
)
.hasRole("Admin")



/*
 * ============================================================
 * DASHBOARD PROFESIONAL
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/profesional"
)
.hasRole("Profesional")



/*
 * ============================================================
 * DASHBOARD RECEPCIONISTA
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/recepcionista",
        "/api/dashboard/recepcionista/**"
)
.hasRole("Recepcionista")





/*
 * ============================================================
 * GRÁFICOS ADMIN
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/citas-estados",
        "/api/dashboard/facturacion-mensual"
)
.hasRole("Admin")





/*
 * ============================================================
 * GRÁFICO PROFESIONAL
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/profesional/citas-estados"
)
.hasRole("Profesional")





/*
 * ============================================================
 * GRÁFICO RECEPCIONISTA
 * ============================================================
 */

.requestMatchers(
        "/api/dashboard/recepcionista/citas-estados"
)
.hasRole("Recepcionista")

                /*
                 * GESTIÓN DE USUARIOS Y PROFESIONALES (Solo Admin)
                 */
/*
 * GESTIÓN DE USUARIOS
 *
 * Solo administrador.
 */
.requestMatchers("/api/usuarios/**")
.hasRole("Admin")



                    /*
                    * CONSULTA DE PROFESIONALES
                    *
                    * Necesaria para:
                    *
                    * - Crear citas.
                    * - Mostrar profesionales disponibles.
                    *
                    * Permisos:
                    *
                    * Admin.
                    * Profesional.
                    * Recepcionista.
                    */
                    .requestMatchers(
                            HttpMethod.GET,
                            "/api/profesionales/**"
                    )
                    .hasAnyRole(
                            "Admin",
                            "Profesional",
                            "Recepcionista"
                    )



                    /*
                    * ADMINISTRACIÓN DE PROFESIONALES
                    *
                    * Crear.
                    * Actualizar.
                    * Eliminar.
                    *
                    * Solo administrador.
                    */
                    .requestMatchers(
                            "/api/profesionales/**"
                    )
                    .hasRole("Admin")

                /*
                 * PACIENTES
                 */
                .requestMatchers("/api/pacientes/**").hasAnyRole("Admin", "Profesional", "Recepcionista")

                /*
                 * CITAS
                 */
                .requestMatchers("/api/citas/**").hasAnyRole("Admin", "Profesional", "Recepcionista")

                /*
                 * HISTORIA CLÍNICA
                 */
                .requestMatchers("/api/historias/**").hasAnyRole("Admin", "Profesional")

                /*
                 * SERVICIOS
                 */
                /*
 * ============================================================
 * CONSULTA DE SERVICIOS
 *
 * Necesaria para:
 *
 * - Crear citas.
 * - Mostrar servicios disponibles.
 *
 * Permisos:
 *
 * Admin.
 * Profesional.
 * Recepcionista.
 *
 * ============================================================
 */

.requestMatchers(
        HttpMethod.GET,
        "/api/servicios/**"
)
.hasAnyRole(
        "Admin",
        "Profesional",
        "Recepcionista"
)





/*
 * ============================================================
 * ADMINISTRACIÓN DE SERVICIOS
 *
 * Crear.
 * Actualizar.
 * Eliminar.
 *
 * Solo administrador.
 *
 * ============================================================
 */

.requestMatchers(
        "/api/servicios/**"
)
.hasRole("Admin")

                /*
                 * FACTURACIÓN
                 */
                .requestMatchers("/api/facturas/**").hasAnyRole("Admin", "Recepcionista")

                /*
                 * CUALQUIER OTRO ENDPOINT REQUIERE AUTENTICACIÓN
                 * 
                 * NOTA PARA DESARROLLO: Si necesitas desactivar momentáneamente 
                 * la seguridad para pruebas sin token, cambia la siguiente línea a:
                 * .anyRequest().permitAll()
                 */
                .anyRequest().authenticated()
            )

            /*
             * Agrega el filtro JWT antes del filtro
             * estándar de autenticación de Spring.
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