package com.sgcodramagdaortiz.sgcodramagdaortiz.controller;

// ============================================================
// Importación de clases necesarias
// ============================================================

import java.util.List;

// ============================================================
// Importación de anotaciones Spring MVC
// ============================================================

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ============================================================
// Importación de validación
// ============================================================

import jakarta.validation.Valid;

// ============================================================
// Importación de entidad y servicio
// ============================================================

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Servicio;
import com.sgcodramagdaortiz.sgcodramagdaortiz.service.ServicioService;


/**
 * ============================================================
 * SERVICIOCONTROLLER.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Controlador REST encargado de gestionar las operaciones
 * CRUD del módulo Servicios Odontológicos.
 *
 * Endpoints disponibles:
 *
 * GET     /api/servicios
 * GET     /api/servicios/{id}
 * POST    /api/servicios
 * PUT     /api/servicios/{id}
 * DELETE  /api/servicios/{id}
 *
 * ============================================================
 */
@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:5173")
public class ServicioController {


    /**
     * Servicio encargado de la lógica de negocio.
     */
    private final ServicioService servicioService;


    /**
     * Constructor para inyección de dependencias.
     *
     * @param servicioService servicio de servicios odontológicos
     */
    public ServicioController(
            ServicioService servicioService) {

        this.servicioService = servicioService;

    }


    /**
     * ========================================================
     * CONSULTAR TODOS LOS SERVICIOS
     * ========================================================
     *
     * Método HTTP:
     * GET
     *
     * Endpoint:
     * /api/servicios
     *
     * @return lista completa de servicios registrados
     */
    @GetMapping
    public ResponseEntity<List<Servicio>> listarServicios() {


        List<Servicio> servicios =
                servicioService.listarServicios();


        return ResponseEntity.ok(servicios);

    }



    /**
     * ========================================================
     * CONSULTAR SERVICIO POR ID
     * ========================================================
     *
     * Método HTTP:
     * GET
     *
     * Endpoint:
     * /api/servicios/{id}
     *
     * @param idServicio identificador del servicio
     * @return servicio encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> buscarServicioPorId(
            @PathVariable Long id) {


        return servicioService
                .buscarServicioPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());

    }



    /**
     * ========================================================
     * REGISTRAR SERVICIO
     * ========================================================
     *
     * Método HTTP:
     * POST
     *
     * Endpoint:
     * /api/servicios
     *
     * @param servicio información del servicio
     * @return servicio creado
     */
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(
            @Valid @RequestBody Servicio servicio) {


        Servicio nuevoServicio =
                servicioService.guardarServicio(servicio);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoServicio);

    }



    /**
     * ========================================================
     * ACTUALIZAR SERVICIO
     * ========================================================
     *
     * Método HTTP:
     * PUT
     *
     * Endpoint:
     * /api/servicios/{id}
     *
     * @param id identificador del servicio
     * @param servicio datos actualizados
     * @return servicio actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(
            @PathVariable Long id,
            @Valid @RequestBody Servicio servicio) {


        return servicioService
                .actualizarServicio(id, servicio)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());

    }



    /**
     * ========================================================
     * ELIMINAR SERVICIO
     * ========================================================
     *
     * Método HTTP:
     * DELETE
     *
     * Endpoint:
     * /api/servicios/{id}
     *
     * @param id identificador del servicio
     * @return código HTTP correspondiente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(
            @PathVariable Long id) {


        boolean eliminado =
                servicioService.eliminarServicio(id);


        if (eliminado) {

            return ResponseEntity.noContent().build();

        }


        return ResponseEntity.notFound().build();

    }

}