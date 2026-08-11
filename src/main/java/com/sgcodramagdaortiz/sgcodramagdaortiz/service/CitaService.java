/*
============================================================
CitaService.java

Sistema de Gestión de Citas Odontológicas

Servicio encargado de la lógica de negocio
del módulo Citas.

Reglas:

Admin:
- Ve todas las citas.
- Crea citas.
- Edita cualquier cita.
- Elimina cualquier cita.


Recepcionista:
- Ve todas las citas.
- Crea citas.
- Edita cualquier cita.
- Elimina cualquier cita.


Profesional:
- Ve únicamente sus citas.
- Puede crear citas de remisión.
- Solo puede editar/eliminar sus propias citas.

============================================================
*/


package com.sgcodramagdaortiz.sgcodramagdaortiz.service;



import java.time.LocalDate;

import java.util.List;

import java.util.Optional;



import org.springframework.lang.NonNull;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;



import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Cita;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;



import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.CitaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.PacienteRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ProfesionalRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.UsuarioRepository;





@Service
public class CitaService {





    private final CitaRepository citaRepository;


    private final PacienteRepository pacienteRepository;


    private final ProfesionalRepository profesionalRepository;


    private final ServicioRepository servicioRepository;


    private final UsuarioRepository usuarioRepository;









    public CitaService(

            CitaRepository citaRepository,

            PacienteRepository pacienteRepository,

            ProfesionalRepository profesionalRepository,

            ServicioRepository servicioRepository,

            UsuarioRepository usuarioRepository

    ){


        this.citaRepository = citaRepository;


        this.pacienteRepository = pacienteRepository;


        this.profesionalRepository = profesionalRepository;


        this.servicioRepository = servicioRepository;


        this.usuarioRepository = usuarioRepository;


    }









    /*
    ========================================================
    LISTAR CITAS SEGÚN ROL

    Admin:
    Todas.

    Recepcionista:
    Todas.

    Profesional:
    Solo propias.

    ========================================================
    */


    public List<Cita> listarCitas(

            Authentication authentication

    ){



        String rol =

                obtenerRol(authentication);





        if(

            rol.equals("ROLE_Admin")

            ||

            rol.equals("ROLE_Recepcionista")

        ){


            return citaRepository.findAll();


        }






        if(

            rol.equals("ROLE_Profesional")

        ){



            Profesional profesional =

                    obtenerProfesionalUsuario(

                            authentication

                    );





            return citaRepository
                    .findByProfesionalIdProfesional(

                            profesional
                            .getIdProfesional()

                    );


        }






        return List.of();


    }









    /*
    ========================================================
    BUSCAR CITA POR ID

    ========================================================
    */


    public Optional<Cita> buscarCitaPorId(

            @NonNull Long idCita

    ){


        return citaRepository.findById(idCita);


    }









    /*
    ========================================================
    CREAR O ACTUALIZAR CITA

    Un profesional puede crear citas de remisión.

    Ejemplo:

    Odontólogo general
          |
          ↓
    Endodoncista

    ========================================================
    */


    public Cita guardarCita(

            @NonNull Cita cita,

            Authentication authentication

    ){



        Paciente paciente =

                pacienteRepository.findById(

                        cita.getPaciente()
                        .getIdPaciente()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "El paciente no existe"

                    )

                );







        Profesional profesional =

                profesionalRepository.findById(

                        cita.getProfesional()
                        .getIdProfesional()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "El profesional no existe"

                    )

                );







        Servicio servicio =

                servicioRepository.findById(

                        cita.getServicio()
                        .getIdServicio()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "El servicio no existe"

                    )

                );








        cita.setPaciente(

                paciente

        );


        cita.setProfesional(

                profesional

        );


        cita.setServicio(

                servicio

        );







        cita.setDuracionMin(

                servicio.getDuracionMin()

        );







        if(

            cita.getFechaCreacion() == null

        ){


            cita.setFechaCreacion(

                    LocalDate.now()

            );


        }







        cita.setFechaModificacion(

                LocalDate.now()

        );







        if(

            cita.getEstado() == null

            ||

            cita.getEstado().isBlank()

        ){


            cita.setEstado(

                    "PROGRAMADA"

            );


        }







        return citaRepository.save(

                cita

        );


    }









    /*
    ========================================================
    ELIMINAR CITA

    Profesional solo puede eliminar
    sus propias citas.

    ========================================================
    */


    public boolean eliminarCita(

            @NonNull Long idCita,

            Authentication authentication

    ){



        Cita cita =

                citaRepository
                .findById(idCita)

                .orElse(null);





        if(cita == null){


            return false;


        }





        validarPermisoSobreCita(

                cita,

                authentication

        );





        citaRepository.deleteById(

                idCita

        );



        return true;


    }









    /*
    ========================================================
    VALIDAR PERMISOS SOBRE UNA CITA

    Admin:
    Todo permitido.

    Recepcionista:
    Todo permitido.

    Profesional:
    Solo sus citas.

    ========================================================
    */


    private void validarPermisoSobreCita(

            Cita cita,

            Authentication authentication

    ){



        String rol =

                obtenerRol(authentication);






        if(

            rol.equals("ROLE_Admin")

            ||

            rol.equals("ROLE_Recepcionista")

        ){


            return;


        }






        if(

            rol.equals("ROLE_Profesional")

        ){



            Profesional profesional =

                    obtenerProfesionalUsuario(

                            authentication

                    );





            if(

                !cita.getProfesional()
                .getIdProfesional()
                .equals(

                    profesional
                    .getIdProfesional()

                )

            ){



                throw new RuntimeException(

                    "No tiene permisos para modificar esta cita."

                );


            }


        }


    }









    /*
    ========================================================
    OBTENER ROL DEL USUARIO

    ========================================================
    */


    private String obtenerRol(

            Authentication authentication

    ){


        return authentication
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();


    }









    /*
    ========================================================
    OBTENER PROFESIONAL ASOCIADO

    ========================================================
    */


    private Profesional obtenerProfesionalUsuario(

            Authentication authentication

    ){



        Usuario usuario =

                usuarioRepository
                .findByUsername(

                        authentication.getName()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "Usuario no encontrado"

                    )

                );






        return profesionalRepository
                .findByUsuarioIdUsuario(

                        usuario.getIdUsuario()

                )

                .orElseThrow(

                    () -> new RuntimeException(

                            "Profesional no asociado"

                    )

                );


    }





}