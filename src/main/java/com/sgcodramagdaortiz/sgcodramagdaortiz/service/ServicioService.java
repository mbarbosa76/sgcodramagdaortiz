package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.util.List;
import java.util.Optional;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;

/**
 * ============================================================
 * SERVICIOSERVICE.JAVA
 * ============================================================
 *
 * Define las operaciones de negocio para el módulo
 * de Servicios Odontológicos.
 *
 * Esta interfaz es implementada por
 * ServicioServiceImpl.
 */
public interface ServicioService {

    /**
     * Obtiene la lista completa de servicios.
     *
     * @return Lista de servicios.
     */
    List<Servicio> listarServicios();

    /**
     * Busca un servicio por su identificador.
     *
     * @param idServicio Identificador del servicio.
     * @return Servicio encontrado.
     */
    Optional<Servicio> buscarServicioPorId(Long idServicio);

    /**
     * Guarda un nuevo servicio.
     *
     * @param servicio Servicio a registrar.
     * @return Servicio registrado.
     */
    Servicio guardarServicio(Servicio servicio);

    /**
     * Actualiza un servicio existente.
     *
     * @param idServicio Identificador.
     * @param servicio Datos nuevos.
     * @return Servicio actualizado.
     */
    Optional<Servicio> actualizarServicio(
            Long idServicio,
            Servicio servicio);

    /**
     * Elimina un servicio.
     *
     * @param idServicio Identificador.
     * @return true si fue eliminado.
     */
    boolean eliminarServicio(Long idServicio);

}