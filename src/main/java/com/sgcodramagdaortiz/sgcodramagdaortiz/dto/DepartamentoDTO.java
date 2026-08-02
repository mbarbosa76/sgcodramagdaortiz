package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

// Ignora cualquier campo del JSON externo que no esté
// mapeado en esta clase (la API Colombia envía muchos más
// campos de los que necesitamos: superficie, población,
// lista de ciudades, referencias circulares, etc.)
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ============================================================
 * DEPARTAMENTODTO.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * DTO (Data Transfer Object) que representa un departamento
 * de Colombia, tal como lo devuelve la API pública externa
 * "API Colombia" (https://api-colombia.com).
 *
 * Solo se conservan los campos "id" y "name", que son los
 * únicos que necesita el frontend para mostrar el selector
 * de departamentos.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartamentoDTO {

    private Integer id;

    private String name;

    public DepartamentoDTO() {
    }

    public DepartamentoDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}