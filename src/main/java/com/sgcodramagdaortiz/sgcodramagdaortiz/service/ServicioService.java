package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;
import com.sgcodramagdaortiz.sgcodramagdaortiz.repository.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> buscarServicioPorId(Long idServicio) {
        return servicioRepository.findById(idServicio);
    }
}