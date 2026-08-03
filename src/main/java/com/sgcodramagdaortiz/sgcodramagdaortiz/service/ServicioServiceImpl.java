package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;

/**
 * ============================================================
 * SERVICIOSERVICEIMPL.JAVA
 * ============================================================
 *
 * Implementación del servicio correspondiente al módulo
 * Servicios Odontológicos.
 *
 * Contiene la lógica de negocio para registrar,
 * consultar, actualizar y eliminar servicios.
 */
@Service
public class ServicioServiceImpl implements ServicioService {

    /**
     * Repositorio de servicios.
     */
    private final ServicioRepository servicioRepository;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param servicioRepository repositorio de servicios.
     */
    public ServicioServiceImpl(
            ServicioRepository servicioRepository) {

        this.servicioRepository = servicioRepository;

    }

    /**
     * Lista todos los servicios.
     */
    @Override
    public List<Servicio> listarServicios() {

        return servicioRepository.findAll();

    }

    /**
     * Busca un servicio por su ID.
     */
    @Override
    public Optional<Servicio> buscarServicioPorId(
            Long idServicio) {

        return servicioRepository.findById(idServicio);

    }

    /**
     * Guarda un nuevo servicio.
     */
    @Override
    public Servicio guardarServicio(
            Servicio servicio) {

        if (servicio.getFechaCreacion() == null) {

            servicio.setFechaCreacion(
                    LocalDate.now());

        }

        servicio.setFechaModificacion(
                LocalDate.now());

        if (servicio.getActivo() == null) {

            servicio.setActivo(true);

        }

        return servicioRepository.save(servicio);

    }

    /**
     * Actualiza un servicio.
     */
    @Override
    public Optional<Servicio> actualizarServicio(
            Long idServicio,
            Servicio servicio) {

        Optional<Servicio> existente =
                servicioRepository.findById(idServicio);

        if (existente.isEmpty()) {

            return Optional.empty();

        }

        Servicio actual = existente.get();

        actual.setCodServicio(servicio.getCodServicio());
        actual.setNombreServicio(servicio.getNombreServicio());
        actual.setDescripcion(servicio.getDescripcion());
        actual.setCategoria(servicio.getCategoria());
        actual.setDuracionMin(servicio.getDuracionMin());
        actual.setPrecio(servicio.getPrecio());
        actual.setActivo(servicio.getActivo());
        actual.setFechaModificacion(LocalDate.now());

        return Optional.of(
                servicioRepository.save(actual));

    }

    /**
     * Elimina un servicio.
     */
    @Override
    public boolean eliminarServicio(
            Long idServicio) {

        if (servicioRepository.existsById(idServicio)) {

            servicioRepository.deleteById(idServicio);

            return true;

        }

        return false;

    }

}