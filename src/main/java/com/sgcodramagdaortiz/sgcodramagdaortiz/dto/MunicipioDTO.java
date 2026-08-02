package com.sgcodramagdaortiz.sgcodramagdaortiz.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ============================================================
 * MUNICIPIODTO.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * DTO que representa un municipio/ciudad de Colombia, tal
 * como lo devuelve la API pública externa "API Colombia"
 * (https://api-colombia.com).
 *
 * Solo se conservan los campos "id" y "name".
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MunicipioDTO {

    private Integer id;

    private String name;

    public MunicipioDTO() {
    }

    public MunicipioDTO(Integer id, String name) {
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