package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

/**
 * Repositorio para acceder a la tabla servicio.
 */
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}