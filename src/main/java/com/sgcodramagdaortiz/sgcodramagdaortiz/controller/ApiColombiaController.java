package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DepartamentoDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.MunicipioDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.RegionDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.ApiColombiaService;

/**
 * ============================================================
 * APICOLOMBIACONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST que expone, dentro de la API pública de
 * este proyecto, los datos de departamentos y municipios de
 * Colombia obtenidos desde la API pública externa
 * "API Colombia" (https://api-colombia.com).
 *
 * Ruta base:
 *
 * /api/publica/colombia
 *
 * Estos endpoints son consumidos por el frontend
 * (ColombiaService.js) para armar la selección en cascada
 * de Departamento -> Municipio en el formulario del módulo
 * Profesional.
 *
 * Al estar bajo "/api/publica/**", no requieren token JWT
 * (según la configuración actual de SecurityConfig.java).
 */
@RestController
@RequestMapping("/api/publica/colombia")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiColombiaController {

    /**
     * Servicio encargado de consumir la API externa.
     */
    private final ApiColombiaService apiColombiaService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param apiColombiaService servicio de consumo de API Colombia
     */
    public ApiColombiaController(ApiColombiaService apiColombiaService) {
        this.apiColombiaService = apiColombiaService;
    }


    /**
     * Lista todos los departamentos de Colombia.
     *
     * GET http://localhost:8765/api/publica/colombia/departamentos
     *
     * @return 200 OK con la lista de departamentos
     */
    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoDTO>> obtenerDepartamentos() {

        return ResponseEntity.ok(
            apiColombiaService.listarDepartamentos()
        );

    }


    /**
     * Lista los municipios/ciudades de un departamento específico.
     *
     * GET http://localhost:8765/api/publica/colombia/departamentos/{idDepartamento}/municipios
     *
     * @param idDepartamento identificador del departamento (id devuelto por /departamentos)
     * @return 200 OK con la lista de municipios de ese departamento
     */
    @GetMapping("/departamentos/{idDepartamento}/municipios")
    public ResponseEntity<List<MunicipioDTO>> obtenerMunicipiosPorDepartamento(
            @PathVariable Integer idDepartamento) {

        return ResponseEntity.ok(
            apiColombiaService.listarMunicipiosPorDepartamento(idDepartamento)
        );

    }


    /**
     * Lista todas las regiones de Colombia.
     *
     * GET http://localhost:8765/api/publica/colombia/regiones
     *
     * @return 200 OK con la lista de regiones
     */
    @GetMapping("/regiones")
    public ResponseEntity<List<RegionDTO>> obtenerRegiones() {

        return ResponseEntity.ok(
            apiColombiaService.listarRegiones()
        );

    }

}