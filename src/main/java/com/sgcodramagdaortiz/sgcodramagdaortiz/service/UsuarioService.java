package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.util.List;
import java.util.Optional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;


/**
 * Interfaz que define las operaciones
 * disponibles para el módulo Usuario.
 *
 * La implementación se encuentra en:
 *
 * UsuarioServiceImpl.java
 */
public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Optional<Usuario> buscarUsuarioPorId(Integer idUsuario);

    Optional<Usuario> buscarPorUsername(String username);

    Usuario guardarUsuario(Usuario usuario);

    Optional<Usuario> actualizarUsuario(Integer idUsuario, Usuario usuario);

    boolean eliminarUsuario(Integer idUsuario);

}