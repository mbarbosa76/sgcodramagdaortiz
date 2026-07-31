package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

/**
 * Repositorio JPA para la entidad Usuario.
 *
 * Proporciona automáticamente las operaciones básicas
 * de acceso a datos (CRUD) mediante Spring Data JPA.
 *
 * IMPORTANTE:
 * Se utiliza Integer como tipo de la llave primaria
 * porque la columna id_usuario es INT UNSIGNED en MySQL.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su nombre de usuario (username).
     *
     * Utilizado principalmente durante el proceso
     * de autenticación (login) y por JwtAuthenticationFilter.
     *
     * @param username nombre de usuario a buscar
     * @return usuario encontrado, si existe
     */
    Optional<Usuario> findByUsername(String username);
}