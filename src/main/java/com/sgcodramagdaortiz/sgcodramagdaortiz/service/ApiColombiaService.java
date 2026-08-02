package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa Arrays para convertir arreglos en listas
import java.util.Arrays;
import java.util.List;

// Importa Service para marcar esta clase como componente de servicio en Spring
import org.springframework.stereotype.Service;

// Importa RestTemplate, usado para realizar peticiones
// HTTP hacia la API externa
import org.springframework.web.client.RestTemplate;

// Importa los DTO que representan la información
// que se recibe desde la API externa
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.DepartamentoDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.MunicipioDTO;
import com.sgcodramagdaortiz.sgcodramagdaortiz.dto.RegionDTO;

/**
 * ============================================================
 * APICOLOMBIASERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Servicio encargado de CONSUMIR la API pública externa
 * "API Colombia" (https://api-colombia.com), que provee
 * información abierta sobre Colombia (departamentos,
 * municipios/ciudades, regiones, entre otros).
 *
 * FINALIDAD DENTRO DEL PROYECTO:
 *
 * Este servicio permite que el módulo Profesional pueda
 * ofrecer, en su formulario, una selección en cascada de
 * Departamento -> Municipio, sin necesidad de mantener
 * manualmente esa información dentro de la base de datos
 * propia del proyecto.
 *
 * FUNCIONAMIENTO:
 *
 * El backend de este proyecto (Spring Boot) actúa como
 * intermediario ("proxy") entre el frontend (React) y la
 * API externa:
 *
 * Frontend (React)
 *      -> Backend propio (ApiColombiaController)
 *           -> API externa (https://api-colombia.com)
 *
 * Esto evita problemas de CORS en el navegador y permite
 * controlar/adaptar la respuesta antes de enviarla
 * al frontend.
 */
@Service
public class ApiColombiaService {

    /**
     * URL base de la API pública externa "API Colombia".
     */
    private static final String BASE_URL_API_COLOMBIA =
        "https://api-colombia.com/api/v1";

    /**
     * Cliente HTTP utilizado para consumir la API externa.
     * Se inyecta automáticamente (ver RestTemplateConfig.java).
     */
    private final RestTemplate restTemplate;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param restTemplate cliente HTTP para consumir la API externa
     */
    public ApiColombiaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Consulta el listado completo de departamentos de Colombia.
     *
     * Petición externa consumida:
     *
     * GET https://api-colombia.com/api/v1/Department
     *
     * @return lista de departamentos (id y nombre)
     */
    public List<DepartamentoDTO> listarDepartamentos() {

        DepartamentoDTO[] respuesta = restTemplate.getForObject(
            BASE_URL_API_COLOMBIA + "/Department",
            DepartamentoDTO[].class
        );

        return Arrays.asList(respuesta);

    }


    /**
     * Consulta el listado de municipios/ciudades que
     * pertenecen a un departamento específico.
     *
     * Petición externa consumida:
     *
     * GET https://api-colombia.com/api/v1/Department/{idDepartamento}/cities
     *
     * @param idDepartamento identificador del departamento en la API externa
     * @return lista de municipios (id y nombre)
     */
    public List<MunicipioDTO> listarMunicipiosPorDepartamento(
            Integer idDepartamento) {

        MunicipioDTO[] respuesta = restTemplate.getForObject(
            BASE_URL_API_COLOMBIA + "/Department/" + idDepartamento + "/cities",
            MunicipioDTO[].class
        );

        return Arrays.asList(respuesta);

    }


    /**
     * Consulta el listado de regiones de Colombia.
     *
     * Petición externa consumida:
     *
     * GET https://api-colombia.com/api/v1/Region
     *
     * @return lista de regiones (id y nombre)
     */
    public List<RegionDTO> listarRegiones() {

        RegionDTO[] respuesta = restTemplate.getForObject(
            BASE_URL_API_COLOMBIA + "/Region",
            RegionDTO[].class
        );

        return Arrays.asList(respuesta);

    }

}