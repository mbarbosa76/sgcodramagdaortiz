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
 * ============================================================
 * SECURITY CONFIG
 * ============================================================
 *
 * Configuración principal de seguridad Spring Security.
 *
 * Responsabilidades:
 *
 * - Validar autenticación JWT.
 * - Controlar acceso por roles.
 * - Proteger endpoints.
 * - Permitir recursos públicos necesarios.
 *
 * ============================================================
 */
@Configuration
public class SecurityConfig {



    private final JwtAuthenticationFilter jwtAuthenticationFilter;



    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ){

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

    }





    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {



        http

        /*
         * API REST con JWT:
         * No utiliza sesión tradicional.
         */
        .csrf(csrf -> csrf.disable())



        /*
         * Permite comunicación con React.
         */
        .cors(cors -> {})



        /*
         * Aplicación Stateless:
         * Cada petición debe traer su JWT.
         */
        .sessionManagement(session ->
            session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
            )
        )



        /*
         * Manejo de errores:
         *
         * 401:
         * Usuario sin autenticación.
         *
         * 403:
         * Usuario autenticado sin permisos.
         */
        .exceptionHandling(exception -> {


            exception.authenticationEntryPoint(

                (request,response,authException)->{


                    response.setStatus(
                            HttpServletResponse.SC_UNAUTHORIZED
                    );


                    response.getWriter()
                            .write(
                                "No autenticado"
                            );


                }

            );



            exception.accessDeniedHandler(

                (request,response,accessDeniedException)->{


                    response.setStatus(
                            HttpServletResponse.SC_FORBIDDEN
                    );


                    response.getWriter()
                            .write(
                                "No tiene permisos para acceder a este recurso"
                            );


                }

            );


        })





        /*
         * ====================================================
         * AUTORIZACIÓN DE ENDPOINTS
         * ====================================================
         */
        .authorizeHttpRequests(auth -> auth





            /*
             * =================================================
             * LOGIN PÚBLICO
             * =================================================
             */
            .requestMatchers(
                    "/api/auth/login"
            )
            .permitAll()






            /*
             * =================================================
             * API COLOMBIA PÚBLICA
             *
             * Utilizada por:
             *
             * Módulo Profesional
             *
             * Funciones:
             *
             * - Departamentos.
             * - Municipios.
             * - Regiones.
             *
             * No requiere JWT porque consume
             * información geográfica pública.
             * =================================================
             */
            .requestMatchers(
                    "/api/publica/colombia/**"
            )
            .permitAll()






            /*
             * CORS PRE-FLIGHT
             */
            .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
            )
            .permitAll()






            /*
             * =================================================
             * DASHBOARD ADMIN
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/admin"
            )
            .hasRole("Admin")



            /*
             * =================================================
             * DASHBOARD PROFESIONAL
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/profesional"
            )
            .hasRole("Profesional")



            /*
             * =================================================
             * DASHBOARD RECEPCIONISTA
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/recepcionista",
                    "/api/dashboard/recepcionista/**"
            )
            .hasRole("Recepcionista")





            /*
             * =================================================
             * GRÁFICOS ADMIN
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/citas-estados",
                    "/api/dashboard/facturacion-mensual"
            )
            .hasRole("Admin")





            /*
             * =================================================
             * GRÁFICO PROFESIONAL
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/profesional/citas-estados"
            )
            .hasRole("Profesional")





            /*
             * =================================================
             * GRÁFICO RECEPCIONISTA
             * =================================================
             */
            .requestMatchers(
                    "/api/dashboard/recepcionista/citas-estados"
            )
            .hasRole("Recepcionista")







            /*
             * =================================================
             * USUARIOS
             *
             * Solo administrador.
             * =================================================
             */
            .requestMatchers(
                    "/api/usuarios/**"
            )
            .hasRole("Admin")







            /*
             * =================================================
             * PROFESIONALES
             *
             * Consulta:
             * Admin
             * Profesional
             * Recepcionista
             *
             * Administración:
             * Solo Admin
             * =================================================
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



            .requestMatchers(
                    "/api/profesionales/**"
            )
            .hasRole("Admin")







            /*
             * =================================================
             * PACIENTES
             * =================================================
             */
            .requestMatchers(
                    "/api/pacientes/**"
            )
            .hasAnyRole(
                    "Admin",
                    "Profesional",
                    "Recepcionista"
            )







            /*
             * =================================================
             * CITAS
             * =================================================
             */
            .requestMatchers(
                    "/api/citas/**"
            )
            .hasAnyRole(
                    "Admin",
                    "Profesional",
                    "Recepcionista"
            )







            /*
             * =================================================
             * HISTORIA CLÍNICA
             * =================================================
             */
            .requestMatchers(
                    "/api/historias/**"
            )
            .hasAnyRole(
                    "Admin",
                    "Profesional"
            )







            /*
             * =================================================
             * SERVICIOS
             *
             * Consulta:
             * Todos los roles operativos.
             *
             * Administración:
             * Solo Admin.
             * =================================================
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



            .requestMatchers(
                    "/api/servicios/**"
            )
            .hasRole("Admin")







            /*
             * =================================================
             * FACTURACIÓN
             * =================================================
             */
            .requestMatchers(
                    "/api/facturas/**"
            )
            .hasAnyRole(
                    "Admin",
                    "Recepcionista"
            )







            /*
             * Cualquier otro endpoint:
             * requiere autenticación.
             */
            .anyRequest()
            .authenticated()


        )







        /*
         * Filtro JWT antes del filtro estándar.
         */
        .addFilterBefore(

                jwtAuthenticationFilter,

                UsernamePasswordAuthenticationFilter.class

        );



        return http.build();


    }







    /**
     * Encoder BCrypt para contraseñas.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){


        return new BCryptPasswordEncoder();


    }


}