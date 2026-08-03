package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

/**
 * ============================================================
 * SERVICIOREPOSITORY.JAVA
 * ============================================================
 *
 * Repositorio encargado de acceder a la información de los
 * servicios odontológicos almacenados en la base de datos.
 *
 * Hereda todos los métodos CRUD de JpaRepository.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

}