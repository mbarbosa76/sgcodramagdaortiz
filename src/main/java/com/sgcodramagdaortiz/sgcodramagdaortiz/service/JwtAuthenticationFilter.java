/*
============================================================
JWT AUTHENTICATION FILTER

Sistema de Gestión de Citas Odontológicas

Filtro encargado de interceptar las peticiones HTTP,
validar el token JWT y registrar la autenticación
dentro del contexto de Spring Security.

El token debe enviarse mediante:

Authorization: Bearer TOKEN

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.service;



import java.io.IOException;


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





@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {




    /*
    ========================================================
    SERVICIOS UTILIZADOS
    ========================================================
    */


    private final JwtService jwtService;


    private final UsuarioRepository usuarioRepository;






    /*
    ========================================================
    CONSTRUCTOR

    Inyección de dependencias.

    ========================================================
    */


    public JwtAuthenticationFilter(

            JwtService jwtService,

            UsuarioRepository usuarioRepository

    ) {


        this.jwtService = jwtService;

        this.usuarioRepository = usuarioRepository;


    }







    /*
    ========================================================
    PROCESAMIENTO DE PETICIONES

    Ejecutado antes de llegar al Controller.

    ========================================================
    */


    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    )

    throws ServletException, IOException {



        /*
        ====================================================
        OBTENER TOKEN JWT
        ====================================================
        */


        String authorizationHeader =

                request.getHeader(
                        "Authorization"
                );





        /*
        ====================================================
        SI NO EXISTE TOKEN

        Continúa el flujo.

        Spring Security decidirá
        si requiere autenticación.

        ====================================================
        */


        if(

            authorizationHeader == null

            ||

            !authorizationHeader.startsWith(
                    "Bearer "
            )

        ){


            filterChain.doFilter(
                    request,
                    response
            );


            return;


        }






        /*
        ====================================================
        EXTRAER TOKEN

        Elimina:

        Bearer 

        dejando solamente JWT.

        ====================================================
        */


        String token =

                authorizationHeader.substring(7);





        try {



            /*
            =================================================
            OBTENER USERNAME DEL TOKEN
            =================================================
            */


            String username =

                    jwtService.obtenerUsername(
                            token
                    );






            if(

                username != null

                &&

                SecurityContextHolder
                .getContext()
                .getAuthentication() == null

            ){



                /*
                =================================================
                BUSCAR USUARIO EN BD
                =================================================
                */


                Usuario usuario =

                        usuarioRepository
                        .findByUsername(username)
                        .orElse(null);







                /*
                =================================================
                VALIDAR USUARIO Y TOKEN
                =================================================
                */


                if(

                    usuario != null

                    &&

                    jwtService.validarToken(
                            token,
                            username
                    )

                ){



                    /*
                    =================================================
                    CREAR AUTORIDAD

                    Ejemplo:

                    Admin

                    se convierte en:

                    ROLE_Admin

                    =================================================
                    */


                    SimpleGrantedAuthority autoridad =

                            new SimpleGrantedAuthority(

                                    "ROLE_"
                                    +
                                    usuario.getRol()

                            );







                    /*
                    =================================================
                    CREAR AUTENTICACIÓN

                    =================================================
                    */


                    UsernamePasswordAuthenticationToken authentication =

                            new UsernamePasswordAuthenticationToken(

                                    usuario.getUsername(),

                                    null,

                                    java.util.List.of(
                                            autoridad
                                    )

                            );







                    /*
                    =================================================
                    GUARDAR AUTENTICACIÓN

                    =================================================
                    */


                    SecurityContextHolder

                            .getContext()

                            .setAuthentication(
                                    authentication
                            );






                    /*
                    =================================================
                    DEPURACIÓN TEMPORAL

                    Solo para verificar roles.

                    Luego se puede eliminar.

                    =================================================
                    */


                    System.out.println(
                            "USUARIO AUTENTICADO: "
                            +
                            usuario.getUsername()
                    );


                    System.out.println(
                            "ROL: "
                            +
                            usuario.getRol()
                    );


                    System.out.println(
                            "AUTORIDAD: "
                            +
                            autoridad.getAuthority()
                    );


                }


            }




        }

        catch(Exception exception){


            /*
            Token inválido o error de validación.

            No se crea autenticación.

            =================================================
            */


        }





        /*
        ====================================================
        CONTINUAR CADENA DE FILTROS
        ====================================================
        */


        filterChain.doFilter(
                request,
                response
        );



    }



}